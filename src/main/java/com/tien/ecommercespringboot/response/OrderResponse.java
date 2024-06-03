package com.tien.ecommercespringboot.response;

import com.tien.ecommercespringboot.entity.OrderStatus;
import com.tien.ecommercespringboot.model.OrderItem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private List<OrderItemResponse> itemList;
}
