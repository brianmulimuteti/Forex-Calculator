package com.forexcalculator.forex.auctionRoom.entity;

import com.forexcalculator.forex.currencyPair.entity.CurrencyPair;
import com.forexcalculator.forex.forexBureau.entity.ForexBureau;
import com.forexcalculator.forex.user.entity.BranchManager;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "auction_room")
public class AuctionRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "corporate_entity_id", nullable = false)
    private BranchManager corporateEntity;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime auctionDate;

    @ManyToOne
    @JoinColumn(name = "forexbureau_id", nullable = false)
    private ForexBureau forexBureau;

    @ManyToOne
    @JoinColumn(name = "currency_pair_id", nullable = false)
    private CurrencyPair currencyPair;

    private double amount;

    private double exchangeRate;
}
