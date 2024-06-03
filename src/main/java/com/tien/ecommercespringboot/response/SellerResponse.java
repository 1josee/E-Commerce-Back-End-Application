package com.tien.ecommercespringboot.response;

import com.tien.ecommercespringboot.entity.ProductEntity;
import com.tien.ecommercespringboot.entity.Role;
import com.tien.ecommercespringboot.entity.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class SellerResponse {

    private Long id;

    private String email;

    private String name;

    private String phone;

    private Role role;

    private Status status;

    private String timeStamp;
}



