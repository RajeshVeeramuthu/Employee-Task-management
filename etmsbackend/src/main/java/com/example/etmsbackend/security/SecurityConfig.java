package com.example.etmsbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.cors().and();

        http.authorizeHttpRequests(auth -> auth

                // AUTH always public
                .requestMatchers("/api/auth/**").permitAll()

                // OPTIONS for Angular
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Only ADMIN can add tasks
                .requestMatchers(HttpMethod.POST, "/api/tasks").hasRole("ADMIN")

                // Both ADMIN & EMPLOYEE can view tasks
                .requestMatchers(HttpMethod.GET, "/api/tasks/**").hasAnyRole("ADMIN", "EMP")

                // EMPLOYEE file upload
                .requestMatchers(HttpMethod.PUT, "/api/tasks/employee-upload/**").hasRole("EMP")

                // EMPLOYEE status change (open → in-progress → completed)
                .requestMatchers(HttpMethod.PUT, "/api/tasks/*/status").hasRole("EMP")

                // ADMIN can update tasks fully
                .requestMatchers(HttpMethod.PUT, "/api/tasks/**").hasRole("ADMIN")

                     // ⭐ ALLOW PROFILE API (VERY IMPORTANT)
            .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()

            // OR use .permitAll() if you prefer
             .requestMatchers(HttpMethod.PUT, "/api/users/profile/").permitAll()
             .requestMatchers(HttpMethod.PUT, "/api/users/update").permitAll()

                // Everything else → Authenticated
                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
