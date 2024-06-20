package com.scaler.userservice.Dtos;

import lombok.Data;

@Data
public class LoginRequestdto {
    private String email;
    private String password;
}
