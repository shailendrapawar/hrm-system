package com.hrm.hrm_system.common.utils;

import com.hrm.hrm_system.common.dtos.JWTUser;
import com.hrm.hrm_system.modules.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.PrivateJwk;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JWTHelper {
    private  final  String SECRET="uk04ac2006-my-super-secret-key-for-jwt-auth";
    private  final long EXPIRATION=1000*60*60*24; // 24 hours

    private SecretKey geySecretKey(){
        return Keys.hmacShaKeyFor(
                SECRET.getBytes()
        );
    }

     Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(geySecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // GENERATE TOKEN
    public String generateToken(UserEntity user){
        List<String> roles=new ArrayList<>();
        return Jwts.builder()
                .subject(user.getId())
                .claim("email",user.getEmail())
                .claim("roles",roles)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()+EXPIRATION)
                )
                .signWith(geySecretKey())// only accept bytes
                .compact();
    }
    public Boolean verifyToken(String token){
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // EXTRACT USER
    public JWTUser extractUser(String token){
        Claims claims = getClaims(token);
        return new JWTUser(

                claims.getSubject(),

                claims.get(
                        "email",
                        String.class
                ),
                claims.get(
                        "roles",
                        List.class
                )
        );
    }
}
