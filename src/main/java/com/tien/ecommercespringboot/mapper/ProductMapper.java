package com.tien.ecommercespringboot.mapper;

import com.tien.ecommercespringboot.entity.CategoryEntity;
import com.tien.ecommercespringboot.entity.ProductEntity;
import com.tien.ecommercespringboot.entity.SellerEntity;
import com.tien.ecommercespringboot.model.Product;
import com.tien.ecommercespringboot.response.ProductResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductMapper {

    public ProductEntity toProductEntity(Product product, Blob image, SellerEntity seller, List<CategoryEntity> categoriesList){
        ProductEntity productEntity = ProductEntity.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(image)
                .length(product.getLength())
                .width(product.getWidth())
                .height(product.getHeight())
                .seller(seller)
                .categories(categoriesList)
                .timeStamp(new Date().toString())
                .build();
        return productEntity;
    }

    public ProductResponse toProductResponse(ProductEntity product){
        byte[] imageBytes = null;
        Blob imageBlob = product.getImage();
        if(imageBlob != null){
            try{
                imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
            } catch (SQLException e) {
                throw new RuntimeException("Error retrieving photo");
            }
        }

        String imageEncode = Base64.encodeBase64String(imageBytes);

        List<String> categoriesList = new ArrayList<>();
        for (CategoryEntity category : product.getCategories()){
            categoriesList.add(category.getName());
        }
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .length(product.getLength())
                .width(product.getWidth())
                .height(product.getHeight())
                .sellerId(product.getSeller().getId())
                .image(imageEncode)
                .categories(categoriesList)
                .timeStamp(product.getTimeStamp())
                .build();
        return productResponse;
    }

    public ProductEntity updateProduct(ProductEntity productEntity, Product product, Blob image, List<CategoryEntity> categoriesList){
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        productEntity.setImage(image);
        productEntity.setLength(product.getLength());
        productEntity.setWidth(product.getWidth());
        productEntity.setHeight(product.getHeight());
        productEntity.setCategories(categoriesList);
        productEntity.setTimeStamp(new Date().toString());
        return productEntity;
    }
}
