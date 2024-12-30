package io.obouh.backend.xpensetrackr.client.classifier.request;

public record Transaction(
        String description,
        Double amount,
        String merchant
) {}