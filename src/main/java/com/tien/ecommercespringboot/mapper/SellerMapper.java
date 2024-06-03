package com.tien.ecommercespringboot.mapper;

import com.tien.ecommercespringboot.entity.Role;
import com.tien.ecommercespringboot.entity.SellerEntity;
import com.tien.ecommercespringboot.entity.Status;
import com.tien.ecommercespringboot.model.Seller;
import com.tien.ecommercespringboot.response.SellerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerMapper {
    private final PasswordEncoder passwordEncoder;
    public SellerEntity toSellerEntity(Seller seller){
        String encodePassword = passwordEncoder.encode(seller.getPassword());
        SellerEntity sellerEntity = SellerEntity.builder()
                .email(seller.getEmail())
                .password(encodePassword)
                .name(seller.getName())
                .phone(seller.getPhone())
                .role(Role.ROLE_SELLER)
                .status(Status.Pending)
                .timeStamp(new Date().toString())
                .build();
        return sellerEntity;
    }

    public SellerResponse toSellerResponse(SellerEntity sellerEntity){
        SellerResponse sellerResponse = SellerResponse.builder()
                .id(sellerEntity.getId())
                .email(sellerEntity.getEmail())
                .name(sellerEntity.getName())
                .phone(sellerEntity.getPhone())
                .role(sellerEntity.getRole())
                .status(sellerEntity.getStatus())
                .timeStamp(sellerEntity.getTimeStamp())
                .build();
        return sellerResponse;
    }
}
