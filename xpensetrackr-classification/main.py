from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from transformers import AutoTokenizer, AutoModelForSequenceClassification
import torch
from typing import List
import uvicorn

app = FastAPI(title="Transaction Classification API")

# Load model and tokenizer
MODEL_NAME = "mgrella/autonlp-bank-transaction-classification-5521155"
tokenizer = AutoTokenizer.from_pretrained(MODEL_NAME)
model = AutoModelForSequenceClassification.from_pretrained(MODEL_NAME)

# Move model to GPU if available
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
model.to(device)

class Transaction(BaseModel):
    description: str
    amount: float
    merchant: str | None = None

class TransactionRequest(BaseModel):
    transactions: List[Transaction]

class ClassificationResponse(BaseModel):
    category: str
    confidence: float

class BatchResponse(BaseModel):
    classifications: List[ClassificationResponse]

def prepare_text(transaction: Transaction) -> str:
    """Prepare transaction text for classification."""
    parts = [transaction.description]
    if transaction.merchant:
        parts.append(transaction.merchant)
    parts.append(str(transaction.amount))
    return " ".join(parts)

def classify_text(text: str) -> ClassificationResponse:
    """Classify a single text using the model."""
    try:
        # Tokenize
        inputs = tokenizer(text, return_tensors="pt", truncation=True, max_length=512)
        inputs = {k: v.to(device) for k, v in inputs.items()}

        # Get prediction
        with torch.no_grad():
            outputs = model(**inputs)
            probs = torch.nn.functional.softmax(outputs.logits, dim=-1)
            confidence, predicted = torch.max(probs, dim=-1)

        # Get category name
        category = model.config.id2label[predicted.item()]

        return ClassificationResponse(
            category=category,
            confidence=confidence.item()
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Classification error: {str(e)}")

@app.post("/classify", response_model=ClassificationResponse)
async def classify_transaction(transaction: Transaction):
    """Classify a single transaction."""
    text = prepare_text(transaction)
    return classify_text(text)

@app.post("/classify/batch", response_model=BatchResponse)
async def classify_batch(request: TransactionRequest):
    """Classify a batch of transactions."""
    classifications = []
    for transaction in request.transactions:
        text = prepare_text(transaction)
        classification = classify_text(text)
        classifications.append(classification)
    return BatchResponse(classifications=classifications)

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {"status": "healthy"}

if __name__ == "__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=8000)