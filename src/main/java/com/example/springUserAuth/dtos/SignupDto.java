package com.example.springUserAuth.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    private String email;
    private String name;
    private String password;
    private int role;
}
