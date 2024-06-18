package com.forexcalculator.forex.forexBureau.repository;

import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForexRepo extends JpaRepository<ForexBureau, Long> {
    Optional<ForexBureau> findByBureauName(String bureauName);
    List<ForexBureau> findByLocation(String location);
}
