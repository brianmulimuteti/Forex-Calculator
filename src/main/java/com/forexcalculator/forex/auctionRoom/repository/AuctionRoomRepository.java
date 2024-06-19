package com.forexcalculator.forex.auctionRoom.repository;

import com.forexcalculator.forex.auctionRoom.entity.AuctionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRoomRepository extends JpaRepository<AuctionRoom, Long> {

    @Query("SELECT ar FROM AuctionRoom ar WHERE ar.corporateEntity.id = :corporateEntityId")
    AuctionRoom findByCorporateEntity(@Param("corporateEntityId") Long corporateEntityId);

    @Query("SELECT ar FROM AuctionRoom ar WHERE ar.forexBureau.id = :forexBureauId")
    AuctionRoom findByForexBureau(@Param("forexBureauId") Long forexBureauId);
}
