package com.tien.ecommercespringboot.response;

import com.tien.ecommercespringboot.entity.SellerEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;
    private Integer length;
    private Integer width;
    private Integer height;
    private Long sellerId;
    private List<String> categories;
    private String timeStamp;
}
