package com.tien.ecommercespringboot.response;

import com.sun.jdi.LongValue;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long orderItemId;
    private Long orderId;
    private Long productId;
    private Long sellerId;
    private Integer quantity;
}
