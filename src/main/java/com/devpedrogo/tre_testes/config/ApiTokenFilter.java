package com.devpedrogo.tre_testes.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class ApiTokenFilter extends OncePerRequestFilter {

    @Value("${api.security.token}")
    private String tokenEsperado;

    //O OncePerRequestFilter possui um método nativo chamado shouldNotFilter. Ele avisa ao filtro para nem ser executado quando a requisição for para o Swagger
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Lê o Header padrão de autorização da Web
        String authHeader = request.getHeader("Authorization");

        // 2. Extrai a chave removendo o prefixo "Bearer " (caso exista)
        String tokenRecebido = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            tokenRecebido = authHeader.substring(7); // Remove a palavra "Bearer "
        }

        // 1. Valida se o token foi enviado e se é exatamente igual ao do .env
        if (tokenRecebido == null || !tokenRecebido.equals(tokenEsperado)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("""
                {
                    "status": 401,
                    "error": "Unauthorized",
                    "message": "Token de acesso invalido ou ausente."
                }
                """);
            return; // Interrompe o fluxo
        }

        // 2. CORREÇÃO DO 403: Se o token for VÁLIDO, avisamos o Spring Security que a requisição está autenticada
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                "API_CLIENT", null, Collections.emptyList()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Permite a requisição prosseguir para o Controller
        filterChain.doFilter(request, response);
    }
}