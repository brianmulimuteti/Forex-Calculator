package com.forexcalculator.forex.user.repository;



import com.forexcalculator.forex.user.entity.BranchManager;
import com.forexcalculator.forex.user.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByIdNumber(Integer idNumber);

    Customer findByUsername(String username);
}
