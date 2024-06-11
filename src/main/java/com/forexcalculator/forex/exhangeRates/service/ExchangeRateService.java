package com.forexcalculator.forex.exhangeRates.service;

import com.forexcalculator.forex.config.AuthenticationFacade;
import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import com.forexcalculator.forex.currencyPair.repository.CurrencyPairRepository;
import com.forexcalculator.forex.exhangeRates.entity.ExchangeRates;
import com.forexcalculator.forex.exhangeRates.repository.ExchangeRatesRepo;
import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import com.forexcalculator.forex.user.entity.BranchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateService {
    @Autowired
    private ExchangeRatesRepo exchangeRatesRepo;
    @Autowired
    private AuthenticationFacade authenticationFacade;
    @Autowired
    private CurrencyPairRepository currencyPairRepository;

    private boolean isBranchManager() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof BranchManager;
        }
        return false;
    }

    public List<ExchangeRates> getExchangeRatesByForexBureau(ForexBureau forexBureau) {
        return exchangeRatesRepo.findByForexBureau(forexBureau);
    }
    public List<ExchangeRates> getExchangeRatesByCurrencyPair(CurrencyPair currencyPair) {
        return exchangeRatesRepo.findByCurrencyPair(currencyPair);
    }

    public ExchangeRates saveExchangeRate(ExchangeRates exchangeRates) {
        if (isBranchManager()) {
            CurrencyPair currencyPair = currencyPairRepository.findByBaseCurrencyAndQuoteCurrency(exchangeRates.getCurrencyPair().getBaseCurrency(), exchangeRates.getCurrencyPair().getQuoteCurrency());
            if (currencyPair == null) {
                currencyPair = new CurrencyPair();
                currencyPair.setBaseCurrency(exchangeRates.getCurrencyPair().getBaseCurrency());
                currencyPair.setQuoteCurrency(exchangeRates.getCurrencyPair().getQuoteCurrency());
                currencyPair = currencyPairRepository.save(currencyPair);
            }
            exchangeRates.setCurrencyPair(currencyPair);
            return exchangeRatesRepo.save(exchangeRates);
        } else {
            throw new RuntimeException("Only Branch Managers can create exchange rates");
        }
    }

}
