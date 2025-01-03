package io.obouh.backend.xpensetrackr.transaction.repository;

import io.obouh.backend.xpensetrackr.transaction.domain.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    long deleteByCreateDateBetween(LocalDate from, LocalDate to);
}
