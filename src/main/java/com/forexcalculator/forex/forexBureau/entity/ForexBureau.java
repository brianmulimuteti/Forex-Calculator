package com.forexcalculator.forex.forexBureau.entity;

import com.forexcalculator.forex.exhangeRates.entity.ExchangeRates;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name  = "forex_bureaus")
@Data
public class ForexBureau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String bureauName;

    private String location;

    @OneToMany(mappedBy = "forexBureau", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeRates> exchangeRates;
}
