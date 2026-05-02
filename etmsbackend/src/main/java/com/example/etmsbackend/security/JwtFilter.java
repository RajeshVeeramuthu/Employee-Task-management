package com.example.etmsbackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  public JwtFilter(JwtUtil jwtUtil){ this.jwtUtil = jwtUtil; }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
    String header = req.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
      try {
        var jws = jwtUtil.parse(header.substring(7));
        Claims body = jws.getBody();
        String email = body.getSubject();
        String role = (String) body.get("role");
        var auth = new UsernamePasswordAuthenticationToken(
        email, 
        null, 
        List.of(() -> role)   // keep as-is, DON'T prefix again
);
        SecurityContextHolder.getContext().setAuthentication(auth);
      } catch (Exception e) {
        // ignore; token invalid
      }
    }
    chain.doFilter(req, res);
  }
}
