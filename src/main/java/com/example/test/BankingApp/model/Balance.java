package com.example.test.BankingApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    @Id
    @GeneratedValue
    Integer balanceId;
    String accountNumber;
    Date lastUpdateTimestamp;
    Double balance;
}
