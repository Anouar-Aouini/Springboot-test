package com.fivepoints.springboottest.payload.responses;

import com.fivepoints.springboottest.entities.User;
import lombok.Data;
import lombok.NonNull;

import java.util.Optional;

@Data
public class MessageResponseUser {
    @NonNull
    private String message;
    @NonNull
    private Optional<User> user;
}
