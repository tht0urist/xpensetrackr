package io.obouh.backend.xpensetrackr.transaction.client.gocardless.response;

import java.util.List;

public record Transactions(List<Transaction> booked,
                           List<Transaction> pending) {
}
