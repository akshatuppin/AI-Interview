package com.interview.security;

// import org.springframework.stereotype.Service;
// import io.jsonwebtoken.*;
// import io.jsonwebtoken.Jwt;
// import io.jsonwebtoken.security.Keys;

// import java.nio.charset.StandardCharsets;
// import java.security.Key;
// import java.util.Date;

// import org.hibernate.annotations.SourceType;
// import org.springframework.beans.factory.annotation.Value;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    
    
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email){

        return  Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date(
                    System.currentTimeMillis() + expiration
            ))
        //    .signWith(getSigningKey(), Jwts.SIG.HS256)
            .signWith(getSigningKey())
            .compact();
    }

    public String extractEmail(String token){
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey)getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenVaild(String token){

        try{
            Jwts.parser()
                .verifyWith((javax.crypto.SecretKey)getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;    
        }catch(Exception e){
            return false;
        }
    }

}
