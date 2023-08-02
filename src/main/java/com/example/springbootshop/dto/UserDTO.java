package com.example.springbootshop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String username;

}
