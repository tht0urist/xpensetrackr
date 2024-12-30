package io.obouh.backend.xpensetrackr.client.gocardless.response;

import java.time.LocalDate;

public record Transaction(String transactionId, LocalDate createDate, TransactionAmout amount, String origine) {

}

