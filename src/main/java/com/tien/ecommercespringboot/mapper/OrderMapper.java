package com.tien.ecommercespringboot.mapper;

import com.tien.ecommercespringboot.entity.OrderEntity;
import com.tien.ecommercespringboot.model.OrderItem;
import com.tien.ecommercespringboot.response.OrderItemResponse;
import com.tien.ecommercespringboot.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;
    public OrderResponse toOrderResponse(OrderEntity orderEntity){
        List<OrderItemResponse> orderItemList = orderEntity.getItemList()
                .stream()
                .map(orderItemMapper::toOrderItemResponse)
                .collect(Collectors.toList());
        OrderResponse orderResponse = OrderResponse.builder()
                .id(orderEntity.getId())
                .status(orderEntity.getStatus())
                .itemList(orderItemList)
                .build();
        return orderResponse;
    }
}
