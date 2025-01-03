package io.obouh.backend.xpensetrackr.transaction.client.gocardless.response;

import java.time.LocalDate;
import java.util.List;

public record Transaction(String transactionId, LocalDate bookingDate,
                          TransactionAmount transactionAmount, List<String> remittanceInformationUnstructuredArray) {
}
