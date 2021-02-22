package com.example.test.BankingApp.kafka;

import com.example.test.BankingApp.model.Balance;
import com.example.test.BankingApp.model.Transaction;
import com.example.test.BankingApp.service.BankingAppService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.test.BankingApp.model.BankingApiCommon.TOTAL_TOPIC_BALANCE;
import static com.example.test.BankingApp.model.BankingApiCommon.TOTAL_TOPIC_TRANSACTION;

@Component
@Slf4j
public class ConsumerImpl {
    @Autowired
    BankingAppService bankingAppService;

    @Autowired
    ObjectMapper objectMapper;


    @KafkaListener(topics = TOTAL_TOPIC_TRANSACTION)
    public void getTransMessage(ConsumerRecord<String,String> consumerRecord) throws JsonProcessingException {
        log.info(" transaction message:"+consumerRecord.toString());
        Transaction transaction = objectMapper.readValue(consumerRecord.value(), Transaction.class);
        bankingAppService.saveTransaction(transaction);
    }

    @KafkaListener(topics = TOTAL_TOPIC_BALANCE)
    public void receiveBalanceMessage(ConsumerRecord<String,String> consumerRecord) throws JsonProcessingException {
        log.info("balance message:"+consumerRecord.toString());
        Balance balance = objectMapper.readValue(consumerRecord.value(),Balance.class);
        bankingAppService.saveBalance(balance);
    }
}
