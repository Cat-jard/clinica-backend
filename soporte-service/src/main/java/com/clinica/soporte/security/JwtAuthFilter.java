package com.clinica.soporte.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * Valida el JWT emitido por usuario-service usando la clave compartida y coloca
 * el rol del token como authority. Es stateless: no consulta base de datos, la
 * identidad (email + rol) se lee directamente de los claims del token.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final SecretKey clave;

    public JwtAuthFilter(@Value("${app.jwt.secret}") String secret) {
        this.clave = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                Claims claims = Jwts.parser()
                        .verifyWith(clave)
                        .build()
                        .parseSignedClaims(header.substring(7))
                        .getPayload();

                String email = claims.getSubject();
                String rol = claims.get("rol", String.class);
                if (email != null && rol != null) {
                    var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));
                    var auth = new UsernamePasswordAuthenticationToken(email, null, authorities);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception ignored) {
                // Token invalido -> se continua sin autenticacion (los endpoints protegidos responden 401/403)
            }
        }

        filterChain.doFilter(request, response);
    }
}
