package com.forexcalculator.forex.user.entity;

import com.forexcalculator.forex.user.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "Customer")
@AllArgsConstructor
@Data
public class Customer {

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
    @NotNull("id number cannot be null")
    private Integer idNumber;

    @Column(unique = true)
    @NotNull("phone number cannot be null")
    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    private Role Role;

    public Customer() {
    }

}
