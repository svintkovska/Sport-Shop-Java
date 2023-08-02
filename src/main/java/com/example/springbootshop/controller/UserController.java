package com.example.springbootshop.controller;

import com.example.springbootshop.dto.UserDTO;
import com.example.springbootshop.entities.User;
import com.example.springbootshop.facade.UserFacade;
import com.example.springbootshop.services.UserService;
import com.example.springbootshop.validation.ResponseErrorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final ResponseErrorValidation validation;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserFacade userFacade;

    public UserController(UserService userService, ResponseErrorValidation validation, BCryptPasswordEncoder bCryptPasswordEncoder, UserFacade userFacade) {
        this.userService = userService;
        this.validation = validation;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userFacade = userFacade;
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal){
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId){
        User user = userService.getUserById(userId);
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
