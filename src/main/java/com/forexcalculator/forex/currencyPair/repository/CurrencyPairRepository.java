package com.forexcalculator.forex.currencyPair.repository;

import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {
    List<CurrencyPair> findByBaseCurrency(String baseCurrency);
    List<CurrencyPair> findByQuoteCurrency(String quoteCurrency);
    CurrencyPair findByBaseCurrencyAndQuoteCurrency(String baseCurrency, String quoteCurrency);
}
