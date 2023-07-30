package com.example.springbootshop.security;

import com.example.springbootshop.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        HashMap<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id",Long.toString(user.getId()));
        claimsMap.put("name", user.getName());
        claimsMap.put("username", user.getEmail());

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .addClaims(claimsMap)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 600))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJwt(token);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public Long getUserIdByToken(String token){
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJwt(token).getBody();
        Long id = (Long) claims.get("id");
        return id;

    }
}
