package com.tien.ecommercespringboot.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seller {
    @NotNull
    @Email
    @Size(min = 7, max = 40)
    private String email;
    @NotNull
    @Size(min = 7, max = 30)
    private String password;
    @NotNull
    @Size(min = 4, max = 40)
    private String name;
    @NotNull
    @Size(min = 9, max = 15)
    private String phone;
}
