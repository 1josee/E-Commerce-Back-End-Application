package com.tien.ecommercespringboot.service;

import com.tien.ecommercespringboot.model.OrderItem;
import com.tien.ecommercespringboot.response.OrderItemResponse;
import com.tien.ecommercespringboot.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(List<OrderItem> orderItemList, Long customerId);

    List<OrderResponse> getAllOrdersByUserId(Long customerId);

    OrderResponse getOrderDetail(Long orderId);

    String updateOrderStatus(Long orderId, String action);


    List<OrderItemResponse> getALlOrderItemsBySellerId(Long sellerId);
}
