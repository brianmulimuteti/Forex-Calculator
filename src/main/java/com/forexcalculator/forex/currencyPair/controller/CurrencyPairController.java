package com.forexcalculator.forex.currencyPair.controller;

import com.forexcalculator.forex.config.AuthenticationFacade;
import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import com.forexcalculator.forex.currencyPair.entity.CurrencyPairDto;
import com.forexcalculator.forex.currencyPair.service.CurrencyPairService;
import com.forexcalculator.forex.user.entity.BranchManager;
import com.forexcalculator.forex.util.entity.ResConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currency-pairs")
public class CurrencyPairController {
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private AuthenticationFacade authenticationFacade;

    private static final Logger logger = LoggerFactory.getLogger(CurrencyPairController.class);

    private boolean isBranchManager() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof BranchManager;
        }
        return false;
    }

    @GetMapping("/base-currency/{baseCurrency}")
    public ResponseEntity<?> getCurrencyPairsByBaseCurrency(@PathVariable String baseCurrency) {
        ResConstructor res = new ResConstructor();

        try {
            List<CurrencyPair> currencyPairs = currencyPairService.getCurrencyPairsByBaseCurrency(baseCurrency);
            if (currencyPairs.isEmpty()) {
                res.setMessage("No currency pairs found for base currency: " + baseCurrency);
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Currency pairs fetched successfully");
            res.setData(currencyPairs);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get currency pairs by base currency: " + baseCurrency, e);
            String errorMessage = "An error occurred while fetching currency pairs by base currency: " + baseCurrency;
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/quote-currency/{quoteCurrency}")
    public ResponseEntity<?> getCurrencyPairByQuoteCurrency(@PathVariable String quoteCurrency) {
        ResConstructor res = new ResConstructor();

        try {
            List<CurrencyPair> currencyPairs = currencyPairService.getCurrencyPairsByQuoteCurrency(quoteCurrency);
            if (currencyPairs.isEmpty()) {
                res.setMessage("No currency pairs found for quote currency: " + quoteCurrency);
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Currency pairs fetched successfully");
            res.setData(currencyPairs);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get currency pairs by quote currency: " + quoteCurrency, e);
            String errorMessage = "An error occurred while fetching currency pairs by quote currency: " + quoteCurrency;
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
    @GetMapping("/{baseCurrency}/{quoteCurrency}")
    public ResponseEntity<?> getCurrencyPair(@PathVariable String baseCurrency, @PathVariable String quoteCurrency) {
        ResConstructor res = new ResConstructor();

        try {
            CurrencyPair currencyPair = currencyPairService.getCurrencyPair(baseCurrency, quoteCurrency);
            if (currencyPair == null) {
                res.setMessage("No currency pair found for base currency: " + baseCurrency + " and quote currency: " + quoteCurrency);
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Currency pair fetched successfully");
            res.setData(currencyPair);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get currency pair for base currency: " + baseCurrency + " and quote currency: " + quoteCurrency, e);
            String errorMessage = "An error occurred while fetching currency pair for base currency: " + baseCurrency + " and quote currency: " + quoteCurrency;
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/all-currency-pairs")
    public ResponseEntity<?> getAllCurrencyPairs() {
        ResConstructor res = new ResConstructor();

        try {
            List<CurrencyPair> currencyPairs = currencyPairService.getAllCurrencyPairs();
            if (currencyPairs.isEmpty()) {
                res.setMessage("No currency pairs found");
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Currency pairs fetched successfully");
            res.setData(currencyPairs);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get all currency pairs", e);
            String errorMessage = "An error occurred while fetching all currency pairs";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PostMapping("/save-currency-pair")
    public ResponseEntity<?> saveCurrencyPair(@RequestBody CurrencyPairDto currencyPairDto) {
        ResConstructor res = new ResConstructor();

        if (!isBranchManager()) {
            res.setMessage("Only Branch Managers can create currency pairs");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        }

        try {
            CurrencyPair currencyPair = new CurrencyPair();
            currencyPair.setQuoteCurrency(currencyPairDto.getQuoteCurrency());
            currencyPair.setBaseCurrency(currencyPairDto.getBaseCurrency());
            CurrencyPair savedCurrencyPair = currencyPairService.saveCurrencyPair(currencyPair);
            res.setMessage("Currency Pair created successfully");
            res.setData(savedCurrencyPair);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            logger.error("Failed to currency pair", e);
            String errorMessage = "An error occurred while creating currency pair";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
}