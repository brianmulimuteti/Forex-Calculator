package com.forexcalculator.forex.exhangeRates.repository;

import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import com.forexcalculator.forex.exhangeRates.entity.ExchangeRates;
import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRatesRepo  extends JpaRepository<ExchangeRates, Long> {
    List<ExchangeRates> findByForexBureau(ForexBureau forexBureau);
    List<ExchangeRates> findByCurrencyPair(CurrencyPair currencyPair);

}
