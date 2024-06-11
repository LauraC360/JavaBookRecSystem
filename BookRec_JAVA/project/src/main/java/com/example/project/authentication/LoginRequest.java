package com.example.project.authentication;


//import io.swagger.v3.oas.annotations.media.Schema;/
//import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
