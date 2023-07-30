package com.example.springbootshop.security;

import com.example.springbootshop.entities.User;
import com.example.springbootshop.services.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthProvider implements AuthenticationProvider {
    private final UserService userService;

    public AuthProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();//той що хочу увійти
        User user = userService.findUserByUsername(username);//той що є в базі даних

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        String password = user.getPassword();//база даних
        String userPassword = authentication.getCredentials().toString();//той що вводить пароль

        System.out.println("password = " + password);
        System.out.println("userPassword = " + userPassword);

        if (!userPassword.equals(password)) {
            throw new BadCredentialsException("Bad Credentials");
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        //AuthenticationToken це зареєстрований користувач
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
