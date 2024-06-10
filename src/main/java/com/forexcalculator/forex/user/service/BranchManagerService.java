package com.forexcalculator.forex.user.service;

import com.forexcalculator.forex.user.entity.BranchManager;
import com.forexcalculator.forex.user.repository.BranchManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchManagerService {

    @Autowired
    private BranchManagerRepository branchManagerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public BranchManager getByIdNumber(Integer idNumber) {
        return branchManagerRepository.findByIdNumber(idNumber).orElseThrow();
    }

    public BranchManager getUserByEmail(String email) {
        return branchManagerRepository.findByEmail(email).orElseThrow();
    }

    public List<BranchManager> getAllUsers() {
        return branchManagerRepository.findAll();
    }
    public BranchManager updateUser(BranchManager branchManager) {
        return branchManagerRepository.save(branchManager);
    }

    public void deleteUser(Long id) {
        branchManagerRepository.deleteById(id);
    }

    public BranchManager signUp(BranchManager branchManager) {
        branchManager.setPassword(passwordEncoder.encode(branchManager.getPassword()));
        return branchManagerRepository.save(branchManager);
    }

    public BranchManager login(Integer idNumber, String password) {
        BranchManager branchManager = branchManagerRepository.findByIdNumber(idNumber).orElseThrow();
        if (!passwordEncoder.matches(password, branchManager.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }
        return branchManager;
    }



}
//BranchManagerService userService = new BranchManagerService();
//BranchManager user = userService.getUserByUsername("john");
//if (userService.isUserInRole(user, Role.BRANCH_MANAGER)) {
//        // user is a branch manager
//        } else {
//        // user is not a branch manager
//        }