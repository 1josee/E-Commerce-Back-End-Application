package com.tien.ecommercespringboot.mapper;

import com.tien.ecommercespringboot.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }

    @Test
    public void shouldToProductEntity(){
        List<String> categories = new ArrayList<>();
        categories.add("Shoe");
       Product product = Product.builder()
               .name("Product1")
               .description("Product description")
               .price(10.0)
               .length(10)
               .width(10)
               .height(10)
               .categories(categories)
               .build();

    }

}