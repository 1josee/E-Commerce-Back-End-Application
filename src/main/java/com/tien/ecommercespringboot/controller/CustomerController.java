package com.tien.ecommercespringboot.controller;

import com.tien.ecommercespringboot.model.Customer;
import com.tien.ecommercespringboot.response.CustomerResponse;
import com.tien.ecommercespringboot.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

//    @PostMapping("/register")
//    public ResponseEntity<?> register(
//            @Valid @RequestBody Customer customer
//    ){
//        try{
//            Customer response = customerService.register(customer);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e){
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
//        }
//    }

    @GetMapping("/profile/{customerId}")
    public ResponseEntity<?> getCustomerProfile(
            @PathVariable("customerId") Long customerId
    ){
        try{
            CustomerResponse response = customerService.getCustomerProfile(customerId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exp
    ){
        var errors = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors()
                .forEach(error ->{
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
