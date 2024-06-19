package com.forexcalculator.forex.currencyPair.service;

import com.forexcalculator.forex.config.AuthenticationFacade;
import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import com.forexcalculator.forex.currencyPair.repository.CurrencyPairRepository;
import com.forexcalculator.forex.user.entity.BranchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyPairService {
    @Autowired
    private CurrencyPairRepository currencyPairRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    private boolean isBranchManager() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof BranchManager;
        }
        return false;
    }

    public List<CurrencyPair> getCurrencyPairsByBaseCurrency(String baseCurrency) {
        return currencyPairRepository.findByBaseCurrency(baseCurrency);
    }
    public List<CurrencyPair> getCurrencyPairsByQuoteCurrency(String quoteCurrency) {
        return currencyPairRepository.findByQuoteCurrency(quoteCurrency);
    }

    public CurrencyPair getCurrencyPair(String baseCurrency, String quoteCurrency) {
        return currencyPairRepository.findByBaseCurrencyAndQuoteCurrency(baseCurrency, quoteCurrency);
    }
    public List<CurrencyPair> getAllCurrencyPairs() {
        return currencyPairRepository.findAll();
    }

    public CurrencyPair saveCurrencyPair(CurrencyPair currencyPair) {
        if (isBranchManager()) {
            return currencyPairRepository.save(currencyPair);
        } else {
            throw new RuntimeException("Only Branch Managers can create currency pairs");
        }
    }
}
