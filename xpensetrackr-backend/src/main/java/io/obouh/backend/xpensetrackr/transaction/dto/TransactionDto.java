package io.obouh.backend.xpensetrackr.transaction.dto;

import io.obouh.backend.xpensetrackr.transaction.domain.Transaction;

import java.time.LocalDate;

public record TransactionDto(
        String transactionId,
        LocalDate createDate,
        Double amount,
        String origine,
        String category
) {
    public static TransactionDto toDto(Transaction transaction){
        return new TransactionDto(
                transaction.transactionId(),
                transaction.createDate(),
                transaction.amount(),
                transaction.origine(),
                transaction.category()
        );
    }
}