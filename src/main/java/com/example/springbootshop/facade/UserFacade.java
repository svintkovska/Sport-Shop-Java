package com.example.springbootshop.facade;

import com.example.springbootshop.dto.UserDTO;
import com.example.springbootshop.entities.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());
        return userDTO;

    }
}
