package com.tien.ecommercespringboot.mapper;

import com.tien.ecommercespringboot.entity.OrderEntity;
import com.tien.ecommercespringboot.entity.OrderItemEntity;
import com.tien.ecommercespringboot.entity.ProductEntity;
import com.tien.ecommercespringboot.entity.SellerEntity;
import com.tien.ecommercespringboot.model.OrderItem;
import com.tien.ecommercespringboot.repository.ProductRepository;
import com.tien.ecommercespringboot.repository.SellerRepository;
import com.tien.ecommercespringboot.response.OrderItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemMapper {
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    public OrderItemEntity toOrderItemEntity(OrderItem orderItem, OrderEntity orderEntity){
        ProductEntity productEntity = productRepository.findById(orderItem.getProductId())
                .orElseThrow(()-> new RuntimeException("Product not found"));
        SellerEntity sellerEntity = sellerRepository.findById(orderItem.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                .product(productEntity)
                .seller(sellerEntity)
                .quantity(orderItem.getQuantity())
                .order(orderEntity)
                .build();

        return orderItemEntity;
    }

//    public OrderItem toOrderItemResponse(OrderItemEntity orderItemEntity){
//        OrderItem orderItemResponse = OrderItem.builder()
//                .productId(orderItemEntity.getProduct().getId())
//                .sellerId(orderItemEntity.getSeller().getId())
//                .quantity(orderItemEntity.getQuantity())
//                .build();
//        return orderItemResponse;
//    }

    public OrderItemResponse toOrderItemResponse(OrderItemEntity orderItemEntity){
        OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                .orderItemId(orderItemEntity.getId())
                .orderId(orderItemEntity.getOrder().getId())
                .productId(orderItemEntity.getProduct().getId())
                .sellerId(orderItemEntity.getSeller().getId())
                .quantity(orderItemEntity.getQuantity())
                .build();
        return orderItemResponse;
    }


}
