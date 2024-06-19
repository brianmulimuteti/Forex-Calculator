package com.forexcalculator.forex.transaction.repository;

import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import com.forexcalculator.forex.transaction.entity.Transaction;
import com.forexcalculator.forex.user.entity.BranchManager;
import com.forexcalculator.forex.user.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBranchManager(BranchManager branchManager);
    List<Transaction> findByCustomer(Customer customer);

}
