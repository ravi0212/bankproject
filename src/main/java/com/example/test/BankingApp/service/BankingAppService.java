package com.example.test.BankingApp.service;

import com.example.test.BankingApp.model.Balance;
import com.example.test.BankingApp.model.Transaction;

import java.util.Date;
import java.util.List;

public interface BankingAppService {
    List<Transaction> getTransactions(String type, String accountNumber, Date fromTime, Date toTime);
    Double getLatestBalance(String accountNumber);
    void saveTransaction(Transaction transaction);
    void saveBalance(Balance balance);
    void sendMessage(String topic, String payload);
}
