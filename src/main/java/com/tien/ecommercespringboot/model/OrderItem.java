package com.tien.ecommercespringboot.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @NotNull
    private Long productId;
    @NotNull
    private Long sellerId;
    @NotNull
    private Integer quantity;
}
