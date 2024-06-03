package com.tien.ecommercespringboot.service;

import com.tien.ecommercespringboot.entity.*;
import com.tien.ecommercespringboot.mapper.OrderItemMapper;
import com.tien.ecommercespringboot.mapper.OrderMapper;
import com.tien.ecommercespringboot.model.OrderItem;
import com.tien.ecommercespringboot.repository.CustomerRepository;
import com.tien.ecommercespringboot.repository.OrderItemRepository;
import com.tien.ecommercespringboot.repository.OrderRepository;
import com.tien.ecommercespringboot.repository.SellerRepository;
import com.tien.ecommercespringboot.response.OrderItemResponse;
import com.tien.ecommercespringboot.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final SellerRepository sellerRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final CustomerRepository customerRepository;

    @Override
    public OrderResponse createOrder(List<OrderItem> orderItemList, Long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        OrderEntity orderEntity = new OrderEntity();

        List<OrderItemEntity> orderItemListEntity = orderItemList
                .stream()
                .map(orderItem -> {
                    return orderItemMapper.toOrderItemEntity(orderItem, orderEntity);
                } )
                .collect(Collectors.toList());

        orderEntity.setItemList(orderItemListEntity);
        orderEntity.setCustomer(customerEntity);
        orderEntity.setStatus(OrderStatus.PENDING);


        orderRepository.save(orderEntity);
        List<OrderItemResponse> orderItemResponseList = orderEntity.getItemList()
                .stream()
                .map(orderItemMapper::toOrderItemResponse)
                .collect(Collectors.toList());

        OrderResponse orderResponse = OrderResponse.builder()
                .id(orderEntity.getId())
                .status(orderEntity.getStatus())
                .itemList(orderItemResponseList)
                .build();
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getAllOrdersByUserId(Long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderResponse> orderResponseList = customerEntity.getOrderList()
                .stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
        return orderResponseList;
    }

    @Override
    public List<OrderItemResponse> getALlOrderItemsBySellerId(Long sellerId) {
        SellerEntity sellerEntity = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        List<OrderItemResponse> orderItemResponseList = sellerEntity.getOrderItemList()
                .stream()
                .map(orderItemMapper::toOrderItemResponse)
                .collect(Collectors.toList());
        return orderItemResponseList;
    }


    @Override
    public OrderResponse getOrderDetail(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        OrderResponse orderResponse = orderMapper.toOrderResponse(orderEntity);
        return orderResponse;
    }

    @Override
    public String updateOrderStatus(Long orderId, String action) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if("accept".equals(action)){
            orderEntity.setStatus(OrderStatus.ACCEPTED);
        } else if ("reject".equals(action)) {
            orderEntity.setStatus(OrderStatus.REJECTED);
        } else if ("shipping".equals(action)) {
            orderEntity.setStatus(OrderStatus.SHIPPING);
        } else if ("cancel".equals(action)) {
            orderEntity.setStatus(OrderStatus.CANCEL);
        } else if ("done".equals(action)){
            orderEntity.setStatus(OrderStatus.DONE);
        } else {
            throw new RuntimeException("Invalid action specified");
        }
        orderRepository.save(orderEntity);
        return "Update status order id " + orderId + " successfully";
    }




}
