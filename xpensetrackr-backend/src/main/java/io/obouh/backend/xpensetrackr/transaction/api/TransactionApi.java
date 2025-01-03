package io.obouh.backend.xpensetrackr.transaction.api;

import io.obouh.backend.xpensetrackr.transaction.dto.TransactionDto;
import io.obouh.backend.xpensetrackr.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

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
        LOG.debug("Sync transaction from={} to={}",from, to);
        transactionService.sync(from, to);
    }

    @GetMapping
    public List<TransactionDto> getAllTransactions(){
        return transactionService.findAllTransactions();
    }

    @GetMapping("/categories")
    public List<String> getCategories(){
        return transactionService.findAllTransactions().stream().map(TransactionDto::category).distinct().toList();
    }
}
