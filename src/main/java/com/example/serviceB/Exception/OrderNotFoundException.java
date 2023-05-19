package com.example.serviceB.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class OrderNotFoundException extends Throwable {
    public OrderNotFoundException(String msg) {
        super(msg);
    }
}
