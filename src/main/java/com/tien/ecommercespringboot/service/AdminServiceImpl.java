package com.tien.ecommercespringboot.service;

import com.tien.ecommercespringboot.entity.SellerEntity;
import com.tien.ecommercespringboot.entity.Status;
import com.tien.ecommercespringboot.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final SellerRepository sellerRepository;
    @Override
    public String updateSellerStatus(Long sellerId, String status) {
        SellerEntity sellerEntity = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        if(status.equals("active")){
            sellerEntity.setStatus(Status.Active);
        } else {
            sellerEntity.setStatus(Status.Pending);
        }
        sellerRepository.save(sellerEntity);
        return "Update status successful";
    }
}
