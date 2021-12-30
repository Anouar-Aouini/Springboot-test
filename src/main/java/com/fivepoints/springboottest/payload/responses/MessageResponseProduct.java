package com.fivepoints.springboottest.payload.responses;


import com.fivepoints.springboottest.entities.Product;
import lombok.Data;
import lombok.NonNull;

import java.util.Optional;

@Data
public class MessageResponseProduct {
    @NonNull
    private String message;
    @NonNull
    private Optional<Product> product;
}
