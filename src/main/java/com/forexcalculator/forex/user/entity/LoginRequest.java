package com.forexcalculator.forex.user.entity;

import lombok.Data;

@Data
public class LoginRequest {

    private Integer idNumber;
    private String password;
}
