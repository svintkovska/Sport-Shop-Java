package com.example.springbootshop.controller;

import com.example.springbootshop.security.JwtTokenProvider;
import com.example.springbootshop.security.requests.LoginRequest;
import com.example.springbootshop.security.requests.SignupRequest;
import com.example.springbootshop.security.responses.JwtTokenSuccessResponse;
import com.example.springbootshop.services.UserService;
import com.example.springbootshop.validation.ResponseErrorValidation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthController {


    private final UserService userService;
    private final ResponseErrorValidation validation;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, ResponseErrorValidation validation, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {

        this.userService = userService;
        this.validation = validation;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult bindingResult){
        ResponseEntity<Object> errors = validation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)){
            System.out.println("errors = " + errors);

            return errors;
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = "Bearer " + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtTokenSuccessResponse(true, jwt) );
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequest signupRequest,
                                                   BindingResult bindingResult){
        ResponseEntity<Object> errors = validation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)){
            return errors;
        }
        userService.createUser(signupRequest);

        return ResponseEntity.ok("User registered successfully");
    }
}
