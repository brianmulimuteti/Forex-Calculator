package com.forexcalculator.forex.user.repository;



import com.forexcalculator.forex.user.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    Customer findByIdNumber(Integer idNumber);

    Customer findByUsername(String username);
}
