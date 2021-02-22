package com.example.test.BankingApp.repository;

import com.example.test.BankingApp.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
