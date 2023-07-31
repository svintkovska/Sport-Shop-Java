package com.example.springbootshop.security;

import com.example.springbootshop.entities.User;
import com.example.springbootshop.services.UserDetailsServiceImpl;
import com.example.springbootshop.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private  JwtTokenProvider jwtTokenProvider;
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Autowired

    public void setUserService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
            Long userId = jwtTokenProvider.getUserIdByToken(jwt);
            User userById = userDetailsService.getUserById(userId);

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userById, null, Collections.emptyList());
            authenticationToken.setDetails(
                   new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if(StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")){
            return authorization.split("")[1];
        }
        return null;

    }
}
