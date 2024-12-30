package io.obouh.backend.xpensetrackr.service;

import io.obouh.backend.xpensetrackr.client.classifier.CategoryClassifierClient;
import io.obouh.backend.xpensetrackr.client.classifier.response.ClassificationResponse;
import io.obouh.backend.xpensetrackr.client.gocardless.GoCardlessClient;
import io.obouh.backend.xpensetrackr.client.gocardless.response.Transaction;
import io.obouh.backend.xpensetrackr.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(rollbackFor = Exception.class)
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryClassifierClient categoryClassifierClient;

    private final GoCardlessClient goCardlessClient;

    public TransactionService(TransactionRepository transactionRepository,
                              CategoryClassifierClient categoryClassifierClient, GoCardlessClient goCardlessClient) {
        this.transactionRepository = transactionRepository;
        this.categoryClassifierClient = categoryClassifierClient;
        this.goCardlessClient = goCardlessClient;
    }

    public void sync(LocalDate from, LocalDate to) {
        transactionRepository.deleteByCreateDateBetween(from, to);
        goCardlessClient.findTransactions().transactions().booked().forEach(this::processAndSaveTransaction);
    }

    public void processAndSaveTransaction(Transaction tx) {
        ClassificationResponse predictedCategory = categoryClassifierClient.classifyTransaction(new io.obouh.backend.xpensetrackr.client.classifier.request.Transaction(
                tx.origine(), tx.amount().amount(), null
        ));

        // Build a new Transaction with the predicted category
        io.obouh.backend.xpensetrackr.domain.Transaction txWithCategory = new io.obouh.backend.xpensetrackr.domain.Transaction(
                tx.transactionId(),
                tx.createDate(),
                tx.amount().amount(),
                tx.origine(),
                predictedCategory.category()
        );

        transactionRepository.save(txWithCategory);
    }
}
