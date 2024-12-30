package io.obouh.backend.xpensetrackr.api;

import io.obouh.backend.xpensetrackr.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/transactions")
public class TransactionApi {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionApi.class);
    private final TransactionService transactionService;
    public TransactionApi(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/sync")
    public void sync(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        transactionService.sync(from, to);
    }
}
