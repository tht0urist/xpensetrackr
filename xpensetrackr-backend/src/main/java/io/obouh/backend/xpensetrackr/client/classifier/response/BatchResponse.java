package io.obouh.backend.xpensetrackr.client.classifier.response;

import java.util.List;

public record BatchResponse(
        List<ClassificationResponse> classifications
) {}
