package io.obouh.backend.xpensetrackr.transaction.client.classifier.response;

public record ClassificationResponse(
        String category,
        Double confidence
) {}
