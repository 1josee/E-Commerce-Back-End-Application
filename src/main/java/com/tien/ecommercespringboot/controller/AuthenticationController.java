package com.tien.ecommercespringboot.controller;

import com.tien.ecommercespringboot.model.Admin;
import com.tien.ecommercespringboot.model.Customer;
import com.tien.ecommercespringboot.model.Seller;
import com.tien.ecommercespringboot.request.AuthenticationRequest;
import com.tien.ecommercespringboot.response.AuthenticationResponse;
import com.tien.ecommercespringboot.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register/customer")
    public ResponseEntity<?> customerRegister(
            @Valid @RequestBody Customer customer
    ){
        try{
            AuthenticationResponse authenticationResponse = authenticationService.cusomterRegister(customer);
            return ResponseEntity.ok(authenticationResponse);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }

    }

    @PostMapping("/register/seller")
    public ResponseEntity<?> sellerRegister(
            @Valid @RequestBody Seller seller
    ){
        try{
            AuthenticationResponse authenticationResponse = authenticationService.sellerRegister(seller);
            return ResponseEntity.ok(authenticationResponse);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }

    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> adminRegister(
            @Valid @RequestBody Admin admin
    ){
        try{
            AuthenticationResponse authenticationResponse = authenticationService.adminRegister(admin);
            return ResponseEntity.ok(authenticationResponse);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }

    }

//    @PostMapping("/register/seller")
//    public ResponseEntity<AuthenticationResponse> sellerRegister(
//            @RequestBody Seller seller
//    ){
//        AuthenticationResponse authenticationResponse = authenticationService.sellerRegister(seller);
//        return ResponseEntity.ok(authenticationResponse);
//    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
            ){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return ResponseEntity.ok(authenticationResponse);
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
