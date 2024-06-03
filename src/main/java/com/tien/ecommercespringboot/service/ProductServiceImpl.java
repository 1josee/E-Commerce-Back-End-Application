package com.tien.ecommercespringboot.service;

import com.tien.ecommercespringboot.entity.CategoryEntity;
import com.tien.ecommercespringboot.entity.ProductEntity;
import com.tien.ecommercespringboot.entity.SellerEntity;
import com.tien.ecommercespringboot.entity.Status;
import com.tien.ecommercespringboot.mapper.ProductMapper;
import com.tien.ecommercespringboot.model.Product;
import com.tien.ecommercespringboot.repository.CategoryRepository;
import com.tien.ecommercespringboot.repository.ProductRepository;
import com.tien.ecommercespringboot.repository.SellerRepository;
import com.tien.ecommercespringboot.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final CategoryRepository categoryRepository;

    private final SellerRepository sellerRepository;

    private final ProductMapper productMapper;

    private final ProductRepository productRepository;
    @Override
    public ProductResponse addProduct(Product product, Long sellerId) throws IOException, SQLException {

        SellerEntity sellerEntity = sellerRepository.findById(sellerId).orElseThrow(() -> new UsernameNotFoundException("Seller not found"));

        if(sellerEntity.getStatus() == Status.Pending){
            throw new RuntimeException("Seller status is not Active");
        }

        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        for(String categoryName : product.getCategories()){
            CategoryEntity existsCategory = categoryRepository.findByName(categoryName);
            if(existsCategory != null){
                categoryEntityList.add(existsCategory);
            } else {
                CategoryEntity newCategory = CategoryEntity.builder().name(categoryName).build();
                categoryRepository.save(newCategory);
                categoryEntityList.add(newCategory);
            }
        }
        byte[] photoBytes = product.getImageUpload().getBytes();
        Blob imageBlob = new SerialBlob(photoBytes);
        ProductEntity productEntity = productMapper.toProductEntity(product, imageBlob, sellerEntity, categoryEntityList);
        productRepository.save(productEntity);
        ProductResponse productResponse = productMapper.toProductResponse(productEntity);
        return productResponse;
    }

    @Override
    public ProductResponse getProductDetail(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProductsBySellerId(Long sellerId) {
        SellerEntity seller = sellerRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller not found"));
        List<ProductResponse> listOfProducts = seller.getProduct_list()
                .stream()
                .map(productMapper :: toProductResponse)
                .collect(Collectors.toList());
        return listOfProducts;
    }

    @Override
    public ProductResponse updateProduct(Long productId, Product product) throws IOException, SQLException {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        for(String categoryName : product.getCategories()){
            CategoryEntity existsCategory = categoryRepository.findByName(categoryName);
            if(existsCategory != null){
                categoryEntityList.add(existsCategory);
            } else {
                CategoryEntity newCategory = CategoryEntity.builder().name(categoryName).build();
                categoryRepository.save(newCategory);
                categoryEntityList.add(newCategory);
            }
        }
        byte[] photoBytes = product.getImageUpload().getBytes();
        Blob imageBlob = new SerialBlob(photoBytes);
        ProductEntity updateProduct = productMapper.updateProduct(productEntity, product, imageBlob ,categoryEntityList);
        productRepository.save(productEntity);
        return productMapper.toProductResponse(updateProduct);
    }

    @Override
    public String deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return "Delete product successfully";
    }
}
