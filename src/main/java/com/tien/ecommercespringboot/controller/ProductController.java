package com.tien.ecommercespringboot.controller;

import com.tien.ecommercespringboot.model.Product;
import com.tien.ecommercespringboot.response.ProductResponse;
import com.tien.ecommercespringboot.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add-product")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> addProduct(
            @Valid @RequestParam @NotBlank String name,
            @Valid @RequestParam @NotBlank String description,
            @Valid @RequestParam @NotNull Double price,
            @Valid @RequestParam @NotNull MultipartFile imageUpload,
            @Valid @RequestParam @NotNull @Min(0) Integer length,
            @Valid @RequestParam @NotNull @Min(0) Integer width,
            @Valid @RequestParam @NotNull @Min(0) Integer height,
            @Valid @RequestParam @NotNull Long sellerId,
            @Valid @RequestParam @NotNull List<String> categories
            ) throws SQLException, IOException {

        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .imageUpload(imageUpload)
                .length(length)
                .width(width)
                .height(height)
                .categories(categories)
                .build();
        try{
            ProductResponse response = productService.addProduct(product, sellerId);
            return ResponseEntity.ok(response);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(e.getMessage());
        }
    }

    @GetMapping("/detail/{productId}")
    public ResponseEntity<?> getProductDetail(
            @PathVariable("productId") Long id
    ){
        try{
            ProductResponse response = productService.getProductDetail(id);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/seller-products/{sellerId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> getAllProductsBySellerId(
            @PathVariable("sellerId") Long id
    ){
        try{
            List<ProductResponse> productResponseList = productService.getAllProductsBySellerId(id);
            return ResponseEntity.ok(productResponseList);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/update-product/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> updateProduct(
            @PathVariable("productId") Long productId,
            @Valid @RequestParam @NotBlank String name,
            @Valid @RequestParam @NotBlank String description,
            @Valid @RequestParam @NotNull Double price,
            @Valid @RequestParam @NotNull MultipartFile imageUpload,
            @Valid @RequestParam @NotNull @Min(0) Integer length,
            @Valid @RequestParam @NotNull @Min(0) Integer width,
            @Valid @RequestParam @NotNull @Min(0) Integer height,
            @Valid @RequestParam @NotNull Long sellerId,
            @Valid @RequestParam @NotNull List<String> categories
    ) throws SQLException, IOException {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .imageUpload(imageUpload)
                .length(length)
                .width(width)
                .height(height)
                .categories(categories)
                .build();
        try {
            ProductResponse response = productService.updateProduct(productId, product);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-product/{productId}")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(
            @PathVariable("productId") Long productId
    ){
        try{
            String response = productService.deleteProduct(productId);
            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.FOUND).body(e.getMessage());
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        String errorMessage = "Required request parameter '" + parameterName + "' is not valid";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


}
