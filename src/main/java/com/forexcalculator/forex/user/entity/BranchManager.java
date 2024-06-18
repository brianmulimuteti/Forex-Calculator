package com.forexcalculator.forex.user.entity;

import com.forexcalculator.forex.auctionRoom.entity.AuctionRoom;
import com.forexcalculator.forex.user.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Set;


@Entity
@Table(name = "Branch_Manager")
@AllArgsConstructor
@Data
public class BranchManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull("name cannot be null")
    private String name;

    @Column(unique = true)
    @NotNull("username cannot be null")
    private String username;

    @NotNull("password cannot be null")
    private String password;

    @Column(unique = true)
    @NotNull("email cannot be null")
    private String email;

    @Column(unique = true)
    @NotNull("phone number cannot be null")
    private String phoneNumber;

    @Column(unique = true)
    @NotNull("Bureau cannot be null")
    private String bureauName;

    @Column(unique = true)
    @NotNull("KRA pin cannot be null")
    private String KRAPin;

    @Column(unique = true)
    @NotNull("ID Number cannot be null")
    private Integer idNumber;

    @Enumerated(EnumType.STRING)
    private Role Role;

    @OneToMany(mappedBy = "corporateEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuctionRoom> auctionRoom;
    public BranchManager() {
    }


}
