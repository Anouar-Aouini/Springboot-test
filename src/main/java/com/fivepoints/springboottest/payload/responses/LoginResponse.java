package com.fivepoints.spring.payload.responses;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginResponse {
    @NonNull
    private String token;
    @NonNull
    private String type;
    @NonNull
    private String message;
    @NonNull
    private String email;
}
