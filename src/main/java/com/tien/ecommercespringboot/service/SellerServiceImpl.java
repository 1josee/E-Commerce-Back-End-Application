package com.tien.ecommercespringboot.service;

import com.tien.ecommercespringboot.entity.SellerEntity;
import com.tien.ecommercespringboot.mapper.SellerMapper;
import com.tien.ecommercespringboot.model.Seller;
import com.tien.ecommercespringboot.repository.SellerRepository;
import com.tien.ecommercespringboot.response.SellerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService{
    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;

    @Override
    public SellerResponse register(Seller seller) {
        if (sellerRepository.existsByEmail(seller.getEmail())){
            throw new RuntimeException("The email \"" + seller.getEmail() + "\" is already exists");
        }
        if (sellerRepository.existsByName(seller.getName())){
            throw new RuntimeException("The name \"" + seller.getName() + "\" is already exists");
        }
        SellerEntity sellerEntity = sellerMapper.toSellerEntity(seller);
        sellerRepository.save(sellerEntity);

        return sellerMapper.toSellerResponse(sellerEntity);
    }

    @Override
    public SellerResponse getSellerProfile(Long sellerId) {
        SellerEntity sellerEntity = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        return sellerMapper.toSellerResponse(sellerEntity);
    }
}
