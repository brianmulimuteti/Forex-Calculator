package com.forexcalculator.forex.user.service;

import com.forexcalculator.forex.user.entity.Customer;
import com.forexcalculator.forex.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer signUp(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }
    public Customer login(Integer idNumber, String password, String username) {
        Customer customer = customerRepository.findByIdNumber(idNumber).orElseThrow();
        if (!passwordEncoder.matches(password, customer.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }
        return customer;
    }


    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow();
    }
    public Customer getByIdNumber(Integer idNumber) {
        return customerRepository.findByIdNumber(idNumber).orElseThrow();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
