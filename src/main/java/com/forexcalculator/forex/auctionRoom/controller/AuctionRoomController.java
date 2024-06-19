package com.forexcalculator.forex.auctionRoom.controller;

import com.forexcalculator.forex.auctionRoom.entity.AuctionRoom;
import com.forexcalculator.forex.auctionRoom.service.AuctionRoomService;
import com.forexcalculator.forex.config.AuthenticationFacade;
import com.forexcalculator.forex.user.entity.BranchManager;
import com.forexcalculator.forex.util.entity.ResConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auction-room")
public class AuctionRoomController {
    @Autowired
    private AuctionRoomService auctionRoomService;
    @Autowired
    private AuthenticationFacade authenticationFacade;

    private static final Logger logger = LoggerFactory.getLogger(AuctionRoomController.class);

    private boolean isBranchManager() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication!= null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof BranchManager;
        }
        return false;
    }

    @GetMapping("/get-by-corporate-entity/{corporateEntityId}")
    public ResponseEntity<?> getAuctionRoomByCorporateEntity(@PathVariable Long corporateEntityId) {
        ResConstructor res = new ResConstructor();

        try{
            AuctionRoom auctionRoom = auctionRoomService.findAuctionRoomByCorporateEntity(corporateEntityId);
            if (auctionRoom == null) {
                res.setMessage("No auction room found for corporate entity ID: " + corporateEntityId);
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Auction room fetched successfully");
            res.setData(auctionRoom);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to fetch auction room by corporate entity ID: " + corporateEntityId, e);
            String errorMessage = "An error occurred while fetching auction room by corporate entity ID: " + corporateEntityId;
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/get-by-forex-bureau/{forexBureauId}")
    public ResponseEntity<?> getAuctionRoomByForexBureau(@PathVariable Long forexBureauId) {
        ResConstructor res = new ResConstructor();

        try {
            AuctionRoom auctionRoom = auctionRoomService.findByAuctionRoomByForexBureau(forexBureauId);
            if (auctionRoom == null) {
                res.setMessage("No auction room found for forex bureau ID: " + forexBureauId);
                return ResponseEntity.notFound().build();
            }
            res.setMessage("Auction room fetched successfully");
            res.setData(auctionRoom);
            return ResponseEntity.status(HttpStatus.FOUND).body(res);
        } catch (Exception e) {
            logger.error("Failed to fetch auction room by forex bureau ID: " + forexBureauId, e);
            String errorMessage = "An error occurred while fetching auction room by corporate entity ID: " + forexBureauId;
            res.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
}
