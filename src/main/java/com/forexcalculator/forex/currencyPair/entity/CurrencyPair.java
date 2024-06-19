package com.forexcalculator.forex.currencyPair.entity;

import com.forexcalculator.forex.exhangeRates.entity.ExchangeRates;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Currency_pair")
@Data
public class CurrencyPair {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String baseCurrency;

    @Column(unique = true)
    private String quoteCurrency;

    @OneToMany(mappedBy = "currencyPair")
    private List<ExchangeRates> exchangeRates;
}
