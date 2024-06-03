package com.tien.ecommercespringboot.service;

import com.tien.ecommercespringboot.model.Product;
import com.tien.ecommercespringboot.response.ProductResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    ProductResponse addProduct(Product product, Long sellerId) throws IOException, SQLException;


    ProductResponse getProductDetail(Long id);

    List<ProductResponse> getAllProductsBySellerId(Long id);

    ProductResponse updateProduct(Long productId, Product product) throws IOException, SQLException;

    String deleteProduct(Long productId);
}
