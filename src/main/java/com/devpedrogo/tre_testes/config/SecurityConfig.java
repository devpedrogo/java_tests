package com.devpedrogo.tre_testes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ApiTokenFilter apiTokenFilter;

    public SecurityConfig(ApiTokenFilter apiTokenFilter) {
        this.apiTokenFilter = apiTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            // APIs REST devem ser Stateless (não guardam sessão de usuário)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            // Adiciona o ApiTokenFilter antes dos filtros de autenticação padrão do Spring
            .addFilterBefore(apiTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
