package io.obouh.backend.xpensetrackr.transaction.client.classifier.response;

import java.util.List;

public record BatchResponse(
        List<ClassificationResponse> classifications
) {}
