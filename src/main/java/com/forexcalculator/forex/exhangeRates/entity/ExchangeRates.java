package com.forexcalculator.forex.exhangeRates.entity;

import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import jakarta.persistence.*;
import lombok.Data;

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

    private String currencyPair;

    private Double buyRate;

    private Double sellRate;

}
