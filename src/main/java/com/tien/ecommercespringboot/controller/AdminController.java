package com.tien.ecommercespringboot.controller;

import com.tien.ecommercespringboot.service.AdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PutMapping("/update/seller-status/{sellerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSellerStatus(
            @PathVariable("sellerId") Long sellerId,
            @Valid @RequestParam("status") @NotBlank String status
    ){
        try{
            String response = adminService.updateSellerStatus(sellerId, status);
            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
