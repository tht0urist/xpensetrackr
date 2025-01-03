package io.obouh.backend.xpensetrackr.transaction.service;

import io.obouh.backend.xpensetrackr.transaction.client.gocardless.GoCardlessClient;
import io.obouh.backend.xpensetrackr.transaction.repository.TransactionRepository;
import io.obouh.backend.xpensetrackr.transaction.client.classifier.CategoryClassifierClient;
import io.obouh.backend.xpensetrackr.transaction.client.classifier.response.ClassificationResponse;
import io.obouh.backend.xpensetrackr.transaction.client.gocardless.response.Transaction;
import io.obouh.backend.xpensetrackr.transaction.dto.TransactionDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
        ClassificationResponse predictedCategory = categoryClassifierClient.classifyTransaction(new io.obouh.backend.xpensetrackr.transaction.client.classifier.request.Transaction(
                tx.remittanceInformationUnstructuredArray().get(0),
                tx.transactionAmount().amount(),
                null
        ));

        // Build a new Transaction with the predicted category
        io.obouh.backend.xpensetrackr.transaction.domain.Transaction txWithCategory = new io.obouh.backend.xpensetrackr.transaction.domain.Transaction(
                tx.transactionId(),
                tx.bookingDate(),
                tx.transactionAmount().amount(),
                tx.remittanceInformationUnstructuredArray().get(0),
                predictedCategory.category()
        );

        transactionRepository.save(txWithCategory);
    }

    public List<TransactionDto> findAllTransactions(){
        return transactionRepository.findAll().stream().map(TransactionDto::toDto).toList();
    }
}
