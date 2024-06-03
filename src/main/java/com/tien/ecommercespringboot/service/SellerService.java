package com.tien.ecommercespringboot.service;

import com.tien.ecommercespringboot.model.Seller;
import com.tien.ecommercespringboot.response.SellerResponse;

public interface SellerService {
    SellerResponse register(Seller seller);

    SellerResponse getSellerProfile(Long sellerId);
}
