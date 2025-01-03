package io.obouh.backend.xpensetrackr.transaction.client.classifier;

import io.obouh.backend.xpensetrackr.transaction.client.classifier.request.Transaction;
import io.obouh.backend.xpensetrackr.transaction.client.classifier.request.TransactionRequest;
import io.obouh.backend.xpensetrackr.transaction.client.classifier.response.BatchResponse;
import io.obouh.backend.xpensetrackr.transaction.client.classifier.response.ClassificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "category-classifier",url = "http://localhost:8000")
public interface CategoryClassifierClient {

    @PostMapping("/classify")
    ClassificationResponse classifyTransaction(@RequestBody Transaction transaction);

    @PostMapping("/batch_classify")
    BatchResponse batchClassify(@RequestBody TransactionRequest request);
}
