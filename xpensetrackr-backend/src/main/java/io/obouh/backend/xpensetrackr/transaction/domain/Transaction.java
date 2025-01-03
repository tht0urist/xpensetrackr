package io.obouh.backend.xpensetrackr.transaction.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "transactions")
public record Transaction(
        @Id
        String transactionId,
        @NotNull
        LocalDate createDate,
        @NotNull
        Double amount,
        @NotEmpty
        String origine,
        @NotEmpty
        String category
) {}