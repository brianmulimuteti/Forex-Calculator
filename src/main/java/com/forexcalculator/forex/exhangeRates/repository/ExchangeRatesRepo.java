package com.forexcalculator.forex.exhangeRates.repository;

import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import com.forexcalculator.forex.exhangeRates.entity.ExchangeRates;
import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRatesRepo  extends JpaRepository<ExchangeRates, Long> {
    List<ExchangeRates> findByForexBureau(ForexBureau forexBureau);
    List<ExchangeRates> findByCurrencyPair(CurrencyPair currencyPair);

}
