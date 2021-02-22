package com.example.test.BankingApp.repository;

import com.example.test.BankingApp.model.Balance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountBalanceRepository extends CrudRepository<Balance,Integer> {
    Balance findByAccountNumber(@Param("accountNumber") String accountNumber);
}
