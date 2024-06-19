package com.forexcalculator.forex.transaction.entity;

import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import com.forexcalculator.forex.user.entity.BranchManager;
import com.forexcalculator.forex.user.entity.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch_manager_id")
    private BranchManager branchManager;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "currency_pair_id")
    private CurrencyPair currencyPair;

    private Double amount;

    private Double exchangeRate;

    @Column(updatable = false)
    private LocalDateTime transactionDate;

}
