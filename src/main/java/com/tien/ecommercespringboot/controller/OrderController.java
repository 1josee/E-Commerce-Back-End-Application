package com.tien.ecommercespringboot.controller;

import com.tien.ecommercespringboot.model.OrderItem;
import com.tien.ecommercespringboot.response.OrderItemResponse;
import com.tien.ecommercespringboot.response.OrderResponse;
import com.tien.ecommercespringboot.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create-order/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createOrder(
            @PathVariable("customerId") Long customerId,
            @Valid @RequestBody List<OrderItem> orderItemList
    ){
        try{
            OrderResponse response = orderService.createOrder(orderItemList, customerId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all-orders/customer/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAllOrdersByCustomerID(
            @PathVariable("customerId") Long customerId
    ){
        try{
            List<OrderResponse> response = orderService.getAllOrdersByUserId(customerId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all-orders/seller/{sellerId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllOrderItemsBySellerId(
        @PathVariable("sellerId") Long sellerId
    ) {
        try{
            List<OrderItemResponse> response = orderService.getALlOrderItemsBySellerId(sellerId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/detail/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('SELLER')")
    public ResponseEntity<?> getOrderDetail(
            @PathVariable("orderId") Long orderId
    ){
        try {
            OrderResponse response = orderService.getOrderDetail(orderId);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PutMapping("/order-status/update/{orderId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateOrderStatus(
        @PathVariable("orderId") Long orderId,
        @Valid @RequestParam("action") @NotBlank String action
    ){
        try {
            String response = orderService.updateOrderStatus(orderId, action);
            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        String errorMessage = "Required request parameter '" + parameterName + "' is not valid";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


}
