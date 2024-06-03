package com.tien.ecommercespringboot.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {
    @NotNull
    @Email
    @Size(min = 7, max = 40)
    private String email;
    @NotNull
    @Size(min = 7, max = 30)
    private String password;
}
