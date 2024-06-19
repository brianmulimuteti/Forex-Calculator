package com.forexcalculator.forex.transaction.service;

import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import com.forexcalculator.forex.currencyPair.repository.CurrencyPairRepository;
import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import com.forexcalculator.forex.transaction.entity.Transaction;
import com.forexcalculator.forex.transaction.repository.TransactionRepository;
import com.forexcalculator.forex.user.entity.BranchManager;
import com.forexcalculator.forex.user.entity.Customer;
import com.forexcalculator.forex.user.repository.BranchManagerRepository;
import com.forexcalculator.forex.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BranchManagerRepository branchManagerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CurrencyPairRepository currencyPairRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow();
    }
    public Transaction createTransactionByBranchManager(Integer idNumber, Transaction transactionDetails) {
        BranchManager branchManager = branchManagerRepository.findByIdNumber(idNumber);
        transactionDetails.setBranchManager(branchManager);
        CurrencyPair currencyPair = currencyPairRepository.findById(transactionDetails.getCurrencyPair().getId()).orElseThrow();
        transactionDetails.setCurrencyPair(currencyPair);
        return transactionRepository.save(transactionDetails);
    }
    public Transaction createTransactionByCustomer(Integer idNumber, Transaction transactionDetails) {
        Customer customer = customerRepository.findByIdNumber(idNumber);
        transactionDetails.setCustomer(customer);
        CurrencyPair currencyPair = currencyPairRepository.findById(transactionDetails.getCurrencyPair().getId()).orElseThrow();
        transactionDetails.setCurrencyPair(currencyPair);
        return transactionRepository.save(transactionDetails);
    }
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
    public List<Transaction> getTransactionsByBranchManager(BranchManager branchManager) {
        return transactionRepository.findByBranchManager(branchManager);
    }
    public List<Transaction> getTransactionsByCustomer(Customer customer) {
        return transactionRepository.findByCustomer(customer);
    }
}
