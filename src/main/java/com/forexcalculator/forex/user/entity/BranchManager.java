package com.forexcalculator.forex.user.entity;

import com.forexcalculator.forex.user.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

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
    private Integer IdNumber;

    @Enumerated(EnumType.STRING)
    private Role Role;


}
