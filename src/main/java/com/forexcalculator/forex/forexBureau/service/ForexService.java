package com.forexcalculator.forex.forexBureau.service;

import com.forexcalculator.forex.config.AuthenticationFacade;
import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import com.forexcalculator.forex.forexBureau.repository.ForexRepo;
import com.forexcalculator.forex.user.entity.BranchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForexService {

    @Autowired
    private ForexRepo forexRepo;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    private boolean isBranchManager() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof BranchManager;

        }
        return false;
    }
    public ForexBureau createForexBureau(ForexBureau forexBureau) {
        if (isBranchManager()) {
            return forexRepo.save(forexBureau);
        } else {
            throw new RuntimeException("Only Branch Managers can create forex bureaus");
        }
    }

    public ForexBureau getForexBureauByName(String bureauName) {
        return forexRepo.findByBureauName(bureauName).orElseThrow();
    }

    public List<ForexBureau> getForexBureauByLocation(String location) {
        return forexRepo.findByLocation(location);
    }

    public List<ForexBureau> getAllForexBureaus() {
        return forexRepo.findAll();
    }

    public ForexBureau updateForexBureau(ForexBureau forexBureau) {
        if (isBranchManager()) {
            return forexRepo.save(forexBureau);
        } else {
            throw new RuntimeException("Only Branch Managers can update forex bureaus");
        }
    }

    public void deleteForexBureau(Long id) {
        if (isBranchManager()) {
            forexRepo.deleteById(id);
        } else {
            throw new RuntimeException("Only Branch Managers can delete forex bureaus");
        }
    }



}
