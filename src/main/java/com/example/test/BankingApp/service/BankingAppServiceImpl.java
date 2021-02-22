package com.example.test.BankingApp.service;

import com.example.test.BankingApp.model.Balance;
import com.example.test.BankingApp.model.Transaction;
import com.example.test.BankingApp.repository.AccountBalanceRepository;
import com.example.test.BankingApp.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BankingAppServiceImpl implements BankingAppService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountBalanceRepository accountBalanceRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Autowired

    EntityManager em;

    @Override
    public List<Transaction> getTransactions(String type, String accountNumber, Date fromTime, Date toTime) {
        log.info("Account Number is:" + accountNumber + ", Type is:" + type + ", fromTime:" + fromTime + ", toTime:" + toTime);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = criteriaBuilder.createQuery(Transaction.class);

        Root<Transaction> trans = cq.from(Transaction.class);
        List<Predicate> predicates = new ArrayList<>();

        if (accountNumber != null) {
            predicates.add((criteriaBuilder.equal(trans.get("accountNumber"), accountNumber)));
        }
        if (type != null) {
            predicates.add(criteriaBuilder.equal(trans.get("type"), type));
        }
        if (fromTime != null && toTime != null) {
            predicates.add(criteriaBuilder.between(trans.get("transactionTs"), fromTime, toTime));
        }


        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public Double getLatestBalance(String accountNumber) {
        Double bal = null;
        Balance balance = accountBalanceRepository.findByAccountNumber(accountNumber);
        if (balance != null) {
            log.info("balance is:" + balance.toString());
            bal = balance.getBalance();
        }
        return bal;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void saveBalance(Balance balance) {
        accountBalanceRepository.save(balance);

    }

    @Override
    public void sendMessage(String topic, String payload) {
        log.info("Topic is:" + topic + ", payload is:" + payload);
        kafkaTemplate.send(topic, payload);
    }
}
