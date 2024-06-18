package com.forexcalculator.forex.user.controller;

import com.forexcalculator.forex.user.entity.BranchManager;
import com.forexcalculator.forex.user.entity.LoginRequest;
import com.forexcalculator.forex.user.service.BranchManagerService;
import com.forexcalculator.forex.util.entity.ResConstructor;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/branch-manager")
public class BranchManagerController {
    private static final Logger logger = LoggerFactory.getLogger(BranchManagerController.class);


    @Autowired
    private BranchManagerService branchManagerService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody BranchManager branchManager) {
        ResConstructor res = new ResConstructor();

        try {
            BranchManager createdBranchManager = branchManagerService.signUp(branchManager);

            res.setMessage("BranchManager added successfully");
            res.setData(createdBranchManager);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            logger.error("Failed to create branch Manager", e);
            String errorMessage = "An error occurred while creating the branch Manager";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        ResConstructor res = new ResConstructor();

        try {
            BranchManager authenticatedBranchManager = branchManagerService.login(loginRequest.getIdNumber(),loginRequest.getPassword(), loginRequest.getUsername());

            if (authenticatedBranchManager!= null) {

                String token = generateToken(authenticatedBranchManager);

                res.setMessage("Login Successful");
                res.setData(authenticatedBranchManager);
                res.setToken(token);
                return ResponseEntity.status(HttpStatus.OK).body(res);
            } else {
                res.setMessage("Invalid Credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
            }
        } catch (Exception e) {
            logger.error("Failed to authenticate branch manager", e);
            String errorMessage = "An error occurred during login";
            res.setMessage(errorMessage);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ResConstructor res = new ResConstructor();

        try {
            logoutBranchManager(request, response, authentication);
            res.setMessage("Logout Successful");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error during logout", e);
            res.setMessage("An error occurred during logout");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    public void logoutBranchManager(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
    }


    @GetMapping("get-by-ID/{idNumber}")
    public ResponseEntity<?> getUserIdNumber(@PathVariable Integer idNumber) {
        ResConstructor res = new ResConstructor();

        try {
            BranchManager branchManager = branchManagerService.getByIdNumber(idNumber);

            if (branchManager == null) {
                res.setMessage("No such branch Manager by id Number: " + idNumber + "Exists!!");
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Branch Manager Fetched successfully");
            res.setData(branchManager);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get user", e);
            String errorMessage = "An error occurred while fetching the user";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        ResConstructor res = new ResConstructor();

        try {
            BranchManager branchManager = branchManagerService.getUserByEmail(email);

            if (branchManager == null) {
                res.setMessage("No such branchManager by email " + email + "Exists!!");
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Branch Manager Fetched successfully");
            res.setData(branchManager);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get user", e);
            String errorMessage = "An error occurred while fetching the user";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping
    public ResponseEntity<List<BranchManager>> getAllUsers() {
        List<BranchManager> branchManagers = branchManagerService.getAllUsers();
        return ResponseEntity.ok(branchManagers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUSer(@PathVariable Long id) {
        ResConstructor res = new ResConstructor();
        branchManagerService.deleteUser(id);
        res.setMessage("Branch Manager Deleted Successfully");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-branch-manager/{idNumber}")
    public ResponseEntity<?> updateUser(@PathVariable Integer idNumber, @RequestBody BranchManager branchManager) {
        ResConstructor res = new ResConstructor();

        try {
            BranchManager existingBranchManager = branchManagerService.getByIdNumber(idNumber);
            if (existingBranchManager == null) {
                res.setMessage("Branch Manager not found");
                return ResponseEntity.notFound().build();
            }

            // Update the existing BranchManager with the new details
            existingBranchManager.setUsername(branchManager.getUsername());
            existingBranchManager.setEmail(branchManager.getEmail());
            existingBranchManager.setPhoneNumber(branchManager.getPhoneNumber());
            existingBranchManager.setName(branchManager.getName());
            existingBranchManager.setKRAPin(branchManager.getKRAPin());
            existingBranchManager.setBureauName(branchManager.getBureauName());

            // Update other fields as necessary

            BranchManager updatedBranchManager = branchManagerService.updateUser(existingBranchManager);

            res.setMessage("Branch Manager updated successfully");
            res.setData(updatedBranchManager);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            logger.error("Failed to update branch Manager", e);
            String errorMessage = "An error occurred while updating the branch Manager";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
    private String generateToken(BranchManager branchManager) {
        String token = Jwts.builder()
                .setSubject(branchManager.getUsername())
                .claim("role", branchManager.getRole())
                .setIssuedAt(new Date())
                .setExpiration(DateUtils.addMinutes(new Date(), 60))
                .signWith(SignatureAlgorithm.HS256, "secret-key")
                .compact();

        return token;
    }
}
