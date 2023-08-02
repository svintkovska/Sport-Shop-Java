package com.example.springbootshop.security;

import com.example.springbootshop.entities.User;
import io.jsonwebtoken.*;
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
                .setExpiration(new Date(System.currentTimeMillis()+ 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdByToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token).getBody();
        Long id = Long.parseLong(claims.get("id", String.class));
        return id;
    }
}
