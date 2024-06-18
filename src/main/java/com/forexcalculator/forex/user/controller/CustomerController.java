package com.forexcalculator.forex.user.controller;

import com.forexcalculator.forex.user.entity.Customer;
import com.forexcalculator.forex.user.entity.LoginRequest;
import com.forexcalculator.forex.user.service.CustomerService;
import com.forexcalculator.forex.util.entity.ResConstructor;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(BranchManagerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody Customer customer) {
        ResConstructor res = new ResConstructor();

        try {
            Customer createdCustomer = customerService.signUp(customer);

            res.setMessage("Customer added successfully");
            res.setData(createdCustomer);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            logger.error("Failed to create Customer", e);
            String errorMessage = "An error occurred while creating the Customer";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        ResConstructor res = new ResConstructor();

        try {
            Customer authenticatedCustomer = customerService.login(loginRequest.getIdNumber(),loginRequest.getPassword(), loginRequest.getUsername());

            if (authenticatedCustomer!= null) {
                String token = generateToken(authenticatedCustomer);
                res.setToken(token);
                res.setMessage("Login Successful");
                res.setData(authenticatedCustomer);
                return ResponseEntity.status(HttpStatus.OK).body(res);
            } else {
                res.setMessage("Invalid Credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
            }
        } catch (Exception e) {
            logger.error("Failed to authenticate customer", e);
            String errorMessage = "An error occurred during login";
            res.setMessage(errorMessage);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutCustomer(request, response, authentication);
        return "redirect:/login";
    }

    public void logoutCustomer(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
    }

    @GetMapping("/{idNumber}")
    public ResponseEntity<?> getCustomerByUsername(@PathVariable Integer idNumber) {
        ResConstructor res = new ResConstructor();

        try {
            Customer customer = customerService.getByIdNumber(idNumber);

            if (customer == null) {
                res.setMessage("No such branchManager by id Number: " + idNumber + "Exists!!");
                return ResponseEntity.notFound().build();
            }
            res.setMessage("customer Fetched successfully");
            res.setData(idNumber);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get customer", e);
            String errorMessage = "An error occurred while fetching the customer";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable String email) {
        ResConstructor res = new ResConstructor();

        try {
            Customer customer = customerService.getCustomerByEmail(email);

            if (customer == null) {
                res.setMessage("No such customer by email " + email + "Exists!!");
                return ResponseEntity.notFound().build();
            }
            res.setMessage("customer Fetched successfully");
            res.setData(customer);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get customer", e);
            String errorMessage = "An error occurred while fetching the customer";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customer = customerService.getAllCustomers();
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUSer(@PathVariable Long id) {
        ResConstructor res = new ResConstructor();
        customerService.deleteCustomer(id);
        res.setMessage("Customer Deleted Successfully");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer idNumber, @RequestBody Customer customer) {
        ResConstructor res = new ResConstructor();

        try {
            Customer existingCustomer = customerService.getByIdNumber(idNumber);
            if (existingCustomer == null) {
                res.setMessage("Customer not found");
                return ResponseEntity.notFound().build();
            }


            existingCustomer.setUsername(customer.getUsername());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            existingCustomer.setName(customer.getName());
            // Update other fields as necessary

            Customer updatedCustomer = customerService.updateCustomer(existingCustomer);

            res.setMessage("Customer updated successfully");
            res.setData(updatedCustomer);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            logger.error("Failed to update Customer", e);
            String errorMessage = "An error occurred while updating the Customer";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
    private String generateToken(Customer customer) {
        String token = Jwts.builder()
                .setSubject(customer.getUsername())
                .claim("role", customer.getRole())
                .setIssuedAt(new Date())
                .setExpiration(DateUtils.addMinutes(new Date(), 60))
                .signWith(SignatureAlgorithm.HS256, "secret-key")
                .compact();

        return token;
    }

}
