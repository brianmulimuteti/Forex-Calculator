package com.forexcalculator.forex.exhangeRates.controller;

import com.forexcalculator.forex.config.AuthenticationFacade;
import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import com.forexcalculator.forex.exhangeRates.entity.ExchangeRates;
import com.forexcalculator.forex.exhangeRates.service.ExchangeRateService;
import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import com.forexcalculator.forex.user.entity.BranchManager;
import com.forexcalculator.forex.util.entity.ResConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRatesController {
    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private AuthenticationFacade authenticationFacade;

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRatesController.class);

    private boolean isBranchManager() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof BranchManager;
        }
        return false;
    }

    @GetMapping("/forex-bureau/{bureauName}")
    public ResponseEntity<?> getExchangeRatesByForexBureau(@PathVariable String bureauName) {
        ResConstructor res = new ResConstructor();

        try {
            ForexBureau forexBureau = new ForexBureau();
            forexBureau.setBureauName(bureauName);
            List<ExchangeRates> exchangeRates = exchangeRateService.getExchangeRatesByForexBureau(forexBureau);
            if (exchangeRates.isEmpty()) {
                res.setMessage("No exchange rates found for forex bureau: " + bureauName);
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Exchange rates fetched successfully");
            res.setData(exchangeRates);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get exchange rates by forex bureau: " + bureauName, e);
            String errorMessage = "An error occurred while fetching exchange rates by forex bureau: " + bureauName;
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/currency-pair/{baseCurrency}/{quoteCurrency}")
    public ResponseEntity<?> getExchangeRatesByCurrencyPair(@PathVariable String baseCurrency, @PathVariable String quoteCurrency) {
        ResConstructor res = new ResConstructor();

        try {
            CurrencyPair currencyPair = new CurrencyPair();
            currencyPair.setBaseCurrency(baseCurrency);
            currencyPair.setQuoteCurrency(quoteCurrency);
            List<ExchangeRates> exchangeRates = exchangeRateService.getExchangeRatesByCurrencyPair(currencyPair);
            if (exchangeRates.isEmpty()) {
                res.setMessage("No exchange rates found for currency pair: " + baseCurrency + "/" + quoteCurrency);
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Exchange rates fetched successfully");
            res.setData(exchangeRates);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get exchange rates by currency pair: " + baseCurrency + "/" + quoteCurrency, e);
            String errorMessage = "An error occurred while fetching exchange rates by currency pair: " + baseCurrency + "/" + quoteCurrency;
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PostMapping("/create-exchange-rate")
    public ResponseEntity<?> createExchangeRate(@RequestBody ExchangeRates exchangeRates) {
        ResConstructor res = new ResConstructor();

        try {
            if (!isBranchManager()) {
                throw new RuntimeException("Only Branch Managers can create exchange rates");
            }
            exchangeRates = exchangeRateService.saveExchangeRate(exchangeRates);
            res.setMessage("Exchange rate created successfully");
            res.setData(exchangeRates);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            logger.error("Failed to create exchange rate", e);
            String errorMessage = "An error occurred while creating exchange rate";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
}
