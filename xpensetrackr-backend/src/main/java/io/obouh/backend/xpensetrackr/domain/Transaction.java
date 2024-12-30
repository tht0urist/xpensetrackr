package io.obouh.backend.xpensetrackr.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "transactions")
public record Transaction(
        @Id
        String transactionId,
        LocalDate createDate,
        Double amount,
        String origine,
        String category
) {}