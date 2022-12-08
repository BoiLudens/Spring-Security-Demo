package com.boiludens.demo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    String jwtSecret;

    public String grantToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 6);
        Date expiration = cal.getTime();

        return Jwts.builder()
                .setIssuer("BoiLudens")
                .setSubject(username)
                .setExpiration(expiration)
                .setIssuedAt(new Date())
                .setId(UUID.randomUUID().toString())
                .signWith(key).compact();
    }

    public void verifyToken(String bearerToken) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(bearerToken);
    }
}
