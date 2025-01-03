package io.obouh.backend.xpensetrackr.transaction.client.classifier.request;

public record Transaction(
        String description,
        Double amount,
        String merchant
) {}