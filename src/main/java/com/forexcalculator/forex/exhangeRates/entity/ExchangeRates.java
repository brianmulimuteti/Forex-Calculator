package com.forexcalculator.forex.exhangeRates.entity;

import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "exchange_rates")
@Data
public class ExchangeRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "forex_bureau_id")
    private ForexBureau forexBureau;

    @ManyToOne
    @JoinColumn(name = "currency_pair_id")
    private CurrencyPair currencyPair;

    private Double buyRate;

    private Double sellRate;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp lastUpdated;

}
