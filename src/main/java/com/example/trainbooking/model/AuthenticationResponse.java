package com.example.trainbooking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {

    private String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
}
