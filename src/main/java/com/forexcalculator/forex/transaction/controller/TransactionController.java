package com.forexcalculator.forex.transaction.controller;

import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import com.forexcalculator.forex.currencyPair.repository.CurrencyPairRepository;
import com.forexcalculator.forex.currencyPair.service.CurrencyPairService;
import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import com.forexcalculator.forex.forexBureau.service.ForexService;
import com.forexcalculator.forex.transaction.entity.Transaction;
import com.forexcalculator.forex.transaction.service.TransactionService;
import com.forexcalculator.forex.user.entity.BranchManager;
import com.forexcalculator.forex.user.entity.Customer;
import com.forexcalculator.forex.user.service.BranchManagerService;
import com.forexcalculator.forex.user.service.CustomerService;
import com.forexcalculator.forex.util.entity.ResConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private BranchManagerService branchManagerService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CurrencyPairRepository currencyPairRepository;
    @GetMapping("/all-transactions")
    public ResponseEntity<?> getAllTransactions() {
        ResConstructor res = new ResConstructor();
        try {
            List<Transaction> transactions = transactionService.getAllTransactions();
            res.setMessage("All Transactions fetched successfully");
            res.setData(transactions);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            logger.error("Failed to fetch all transactions", e);
            String errorMessage = "An error occurred while fetching all transactions";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        ResConstructor res = new ResConstructor();
        try{
            Transaction transaction = transactionService.getTransactionById(id);
            if (transaction == null) {
                res.setMessage("Transaction not found");
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Transaction fetched successfully");
            res.setData(transaction);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to fetch transaction by ID: " + id, e);
            String errorMessage = "An error occurred while fetching the transaction by ID: " + id;
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PostMapping("/create-by-branch-manager/{idNumber}")
    public ResponseEntity<?> createTransactionByBranchManager(@RequestParam Integer idNumber, @RequestBody Transaction transactionDetails) {
        ResConstructor res = new ResConstructor();
        try {
            BranchManager branchManager = branchManagerService.getByIdNumber(idNumber);
            if (branchManager == null) {
                res.setMessage("Branch manager not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
            }
            transactionDetails.setBranchManager(branchManager);
            CurrencyPair currencyPair = currencyPairRepository.findById(transactionDetails.getCurrencyPair().getId()).orElseThrow(() -> new RuntimeException("Currency pair not found"));
            transactionDetails.setCurrencyPair(currencyPair);
            Transaction createdTransaction = transactionService.createTransactionByBranchManager(idNumber,transactionDetails);
            res.setMessage("Transaction Created successfully");
            res.setData(createdTransaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            logger.error("Failed to create transaction", e);
            String errorMessage = "An error occurred while creating the transaction";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PostMapping("/create-by-customer/{idNumber}")
    public ResponseEntity<?> createTransactionByCustomer(@RequestParam Integer idNumber, @RequestBody Transaction transactionDetails) {
        ResConstructor res = new ResConstructor();
        try {
            Customer customer = customerService.getByIdNumber(idNumber);
            if (customer == null) {
                res.setMessage("Customer not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
            }
             transactionDetails.setCustomer(customer);
            CurrencyPair currencyPair = currencyPairRepository.findById(transactionDetails.getCurrencyPair().getId()).orElseThrow(() -> new RuntimeException("Currency pair not found"));
            Transaction createdTransaction = transactionService.createTransactionByCustomer(idNumber, transactionDetails);
            res.setMessage("Transaction Created successfully");
            res.setData(createdTransaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            logger.error("Failed to create transaction", e);
            String errorMessage = "An error occurred while creating the transaction";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PutMapping("/update-transaction/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        ResConstructor res = new ResConstructor();
        try {
            transaction.setId(id);
            Transaction updatedTransaction = transactionService.updateTransaction(transaction);
            if (updatedTransaction == null) {
                res.setMessage("Transaction not found");
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Transaction updated successfully");
            res.setData(updatedTransaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            logger.error("Failed to update transaction", e);
            String errorMessage = "An error occurred while updating the transaction";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @DeleteMapping("/delete-transaction/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        ResConstructor res = new ResConstructor();
        try {
            transactionService.deleteTransaction(id);
            res.setMessage("Transaction deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            logger.error("Failed to delete transaction",e);
            String errorMessage = "An error occurred while deleting the transaction";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
    @GetMapping("/get-transaction/branch-manager/{idNumber}")
    public ResponseEntity<?> getTransactionsByBranchManager(@PathVariable Integer idNumber) {
        ResConstructor res = new ResConstructor();
        try {
            BranchManager branchManager = branchManagerService.getByIdNumber(idNumber);
            if (branchManager == null) {
                res.setMessage("Branch manager not found");
                return ResponseEntity.notFound().build();
            }
            List<Transaction> transactions = transactionService.getTransactionsByBranchManager(branchManager);
            res.setMessage("Transactions fetched successfully");
            res.setData(transactions);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to fetch transactions by branch manager", e);
            String errorMessage = "An error occurred while fetching transactions by branch manager";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/get-transaction/customer/{idNumber}")
    public ResponseEntity<?> getTransactionsByCustomer(@PathVariable Integer idNumber) {
        ResConstructor res = new ResConstructor();
        try {
            Customer customer = customerService.getByIdNumber(idNumber);
            if (customer == null) {
                res.setMessage("Customer not found");
                return ResponseEntity.notFound().build();
            }
            List<Transaction> transactions = transactionService.getTransactionsByCustomer(customer);
            res.setMessage("Transactions fetched successfully");
            res.setData(transactions);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to fetch transactions by Customer", e);
            String errorMessage = "An error occurred while fetching transactions by Customer";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
}

