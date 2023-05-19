package com.example.serviceB;

import com.example.serviceB.Exception.InternalErrorException;
import com.example.serviceB.Exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    ResponseEntity<Map> handleOrderNotFoundException(Exception ex, WebRequest webRequest){
        Map<String,Object> body =new HashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("message", ex.getCause().getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalErrorException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<Map> handleInternalErrorException(Exception ex, WebRequest webRequest){
        Map<String,Object> body =new HashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("message", ex.getCause().getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
