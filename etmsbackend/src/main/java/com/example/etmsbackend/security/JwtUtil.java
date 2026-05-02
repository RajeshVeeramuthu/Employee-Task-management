package com.example.etmsbackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
  private final Key key = Keys.hmacShaKeyFor("replace-with-your-very-long-secret-key-of-32+chars".getBytes());
  private final long ttl = 1000L * 60 * 60 * 24; // 24h

  public String generateToken(String subject, Map<String,Object> claims){
    return Jwts.builder()
      .setSubject(subject)
      .addClaims(claims)
      .setExpiration(new Date(System.currentTimeMillis() + ttl))
      .signWith(key)
      .compact();
  }

  public Jws<Claims> parse(String token){
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
  }
}
