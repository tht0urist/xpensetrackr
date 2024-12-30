package io.obouh.backend.xpensetrackr.client.classifier.request;

import java.util.List;

public record TransactionRequest(
        List<Transaction> transactions
) {}
