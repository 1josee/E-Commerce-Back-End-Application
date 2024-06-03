package com.tien.ecommercespringboot.controller;

import com.tien.ecommercespringboot.model.Customer;
import com.tien.ecommercespringboot.model.Seller;
import com.tien.ecommercespringboot.response.SellerResponse;
import com.tien.ecommercespringboot.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

//    @PostMapping("/register")
//    public ResponseEntity<?> register(
//            @Valid @RequestBody Seller seller
//    ){
//        try{
//            SellerResponse response = sellerService.register(seller);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
//        }
//    }

    @GetMapping("/profile/{sellerId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    public ResponseEntity<?> getSellerProfile(
            @PathVariable("sellerId") Long sellerId
    ){
        try{
            SellerResponse response = sellerService.getSellerProfile(sellerId);
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
