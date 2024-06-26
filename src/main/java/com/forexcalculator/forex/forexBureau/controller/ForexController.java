package com.forexcalculator.forex.forexBureau.controller;

import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import com.forexcalculator.forex.forexBureau.service.ForexService;
import com.forexcalculator.forex.user.controller.BranchManagerController;
import com.forexcalculator.forex.util.entity.ResConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/forex-bureaus")
public class ForexController {

    private static final Logger logger = LoggerFactory.getLogger(ForexController.class);

    @Autowired
    private ForexService forexService;

    @PostMapping("/add-bureau")
    public ResponseEntity<?> createForexBureau(@RequestBody ForexBureau forexBureau) {
        ResConstructor res = new ResConstructor();

        try {
            ForexBureau createdForexBureau = forexService.createForexBureau(forexBureau);

            res.setMessage("Forex Bureau Created successfully");
            res.setData(createdForexBureau);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            logger.error("Failed to create forex bureau", e);
            String errorMessage = "An error occurred while creating the forex bureau";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/all-bureaus")
    public ResponseEntity<?> getAllForexBureaus() {
        ResConstructor res = new ResConstructor();

        try {
            List<ForexBureau> forexBureaus = forexService.getAllForexBureaus();

            res.setMessage("Forex Bureaus fetched Successfully");
            res.setData(forexBureaus);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get all forex bureaus", e);
            String errorMessage = "An error occurred while fetching all forex bureaus";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/forex-bureau/{bureauName}")
    public ResponseEntity<?> getForexBureauByName(@PathVariable String bureauName) {
        ResConstructor res = new ResConstructor();

        try {
            ForexBureau forexBureau = forexService.getForexBureauByName(bureauName);
            if (forexBureau == null ) {
                res.setMessage("No such forex bureau by name: " + bureauName + "Exists!!");
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Forex Bureau Fetched successfully");
            res.setData(forexBureau);
            return  ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e)  {
            logger.error("Failed to get forex bureau by name" + bureauName, e);
            String errorMessage = "An error occurred while fetching the forex bureau by name: " + bureauName ;
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<?> getForexBureauByLocation(@PathVariable String location) {
        ResConstructor res = new ResConstructor();

        try {
            List<ForexBureau> forexBureaus = forexService.getForexBureauByLocation(location);

            if (forexBureaus.isEmpty()) {
                res.setMessage("No such Forex bureau by location: " + location + "Exists!!");
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Forex Bureaus Fetched successfully");
            res.setData(forexBureaus);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to get forex bureau by location: " + location, e);
            String errorMessage = "An error occurred while fetching the forex bureau by location: " + location;
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteForexBureau(@PathVariable Long id) {
        ResConstructor res = new ResConstructor();

        try {
            forexService.deleteForexBureau(id);

            res.setMessage("Forex Bureau Deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            logger.error("Failed to delete forex bureau", e);
            String errorMessage = "An error occurred while deleting the forex bureau";
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PutMapping("/update-forex-bureau/{bureauName}")
    public ResponseEntity<?> updateForexBureau(@PathVariable String bureauName, @RequestBody ForexBureau forexBureau) {
        ResConstructor res = new ResConstructor();

        try {
            ForexBureau existingForexBureau = forexService.getForexBureauByName(bureauName);
            if ( existingForexBureau == null) {
                res.setMessage("Forex bureau not found");
                return ResponseEntity.notFound().build();
            }

            existingForexBureau.setBureauName(forexBureau.getBureauName());
            existingForexBureau.setLocation(forexBureau.getLocation());
            existingForexBureau.setExchangeRates(forexBureau.getExchangeRates());

            ForexBureau updatedForexBureau = forexService.updateForexBureau(existingForexBureau);

            res.setMessage("Forex Bureau updated successfully");
            res.setData(updatedForexBureau);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            logger.error("Failed to update Forex Bureau", e);
            String errorMessage = "An error occurred while updating the forex bureau";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
}