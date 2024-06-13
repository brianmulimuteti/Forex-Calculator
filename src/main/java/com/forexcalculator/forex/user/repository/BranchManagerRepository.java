package com.forexcalculator.forex.user.repository;

import java.util.Optional;

import com.forexcalculator.forex.user.entity.BranchManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchManagerRepository extends JpaRepository<BranchManager, Long>{

    Optional<BranchManager> findByEmail(String email);

    BranchManager findByIdNumber(Integer idNumber);

    BranchManager findByUsername(String username);
}
