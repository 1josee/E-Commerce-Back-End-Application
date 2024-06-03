package com.tien.ecommercespringboot.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CustomerResponse {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String address;

}
