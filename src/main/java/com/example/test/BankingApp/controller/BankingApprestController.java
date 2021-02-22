package com.example.test.BankingApp.controller;

import com.example.test.BankingApp.model.Balance;
import com.example.test.BankingApp.model.Transaction;
import com.example.test.BankingApp.service.BankingAppService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.example.test.BankingApp.model.BankingApiCommon.TOTAL_TOPIC_BALANCE;
import static com.example.test.BankingApp.model.BankingApiCommon.TOTAL_TOPIC_TRANSACTION;

@RestController
@RequestMapping("/api")
@Validated
@Slf4j
public class BankingApprestController {

        @Autowired
        ObjectMapper objectMapper;

        @Autowired
        BankingAppService bankingService;

        @PostMapping("/sendTransaction")
        public void sendTransactionMessage(@RequestBody Transaction transaction) throws JsonProcessingException {
            bankingService.sendMessage(TOTAL_TOPIC_TRANSACTION, objectMapper.writeValueAsString(transaction));
        }

        @PostMapping("/sendBalance")
        public void sendBalanceMessage(@RequestBody Balance balance) throws JsonProcessingException {
            log.info("POST request:" + balance.toString());
            bankingService.sendMessage(TOTAL_TOPIC_BALANCE, objectMapper.writeValueAsString(balance));
        }

        @GetMapping("/balance")
        public Double getBalance(String accountNumber) {
            return bankingService.getLatestBalance(accountNumber);
        }

        @GetMapping("/transactions")
        public List<Transaction> getTransactions(String accountNumber, String type, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromTime, @DateTimeFormat(pattern = "yyyy-MM-dd") Date toTime) {
            return bankingService.getTransactions(type,accountNumber, fromTime, toTime);
        }
    }


