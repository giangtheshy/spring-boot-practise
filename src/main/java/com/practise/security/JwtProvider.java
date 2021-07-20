package com.practise.security;

import com.practise.exceptions.SpringException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;

import static io.jsonwebtoken.Jwts.parserBuilder;
import static java.util.Date.from;

@Service
@Data
public class JwtProvider {

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @Value("${public_key}")
    private  String publicKey;

    @Value("${secret_key}")
    private  String secretKey;



    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            byte[] data = Base64.getDecoder().decode((secretKey.getBytes()));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            return (PrivateKey) fact.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new SpringException("Exception occured while retrieving public key from keystore", e);
        }
    }

    public boolean validateToken(String jwt) {
        parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            byte[] data = Base64.getDecoder().decode((publicKey.getBytes()));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            return fact.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new SpringException("Exception occured while " +
                    "retrieving public key from keystore", e);
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
