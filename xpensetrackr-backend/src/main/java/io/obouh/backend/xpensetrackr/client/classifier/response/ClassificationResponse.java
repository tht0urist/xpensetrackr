package io.obouh.backend.xpensetrackr.client.classifier.response;

public record ClassificationResponse(
        String category,
        Double confidence
) {}
