package com.forexcalculator.forex.auctionRoom.service;

import com.forexcalculator.forex.auctionRoom.entity.AuctionRoom;
import com.forexcalculator.forex.auctionRoom.repository.AuctionRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionRoomService {
    @Autowired
    private AuctionRoomRepository auctionRoomRepository;

    public AuctionRoom findAuctionRoomByCorporateEntity(Long corporateEntityId) {
        return auctionRoomRepository.findByCorporateEntity(corporateEntityId);
    }
    public AuctionRoom findByAuctionRoomByForexBureau(Long forexBureauId) {
        return auctionRoomRepository.findByForexBureau(forexBureauId);
    }
}
