package com.tien.ecommercespringboot.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private String description;
    private Double price;
    private MultipartFile imageUpload;
    private Integer length;
    private Integer width;
    private Integer height;
    private List<String> categories;
}
