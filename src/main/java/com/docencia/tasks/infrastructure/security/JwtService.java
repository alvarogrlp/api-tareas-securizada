package com.docencia.tasks.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

/**
 * Servicio encargado de la gestión de tokens JWT.
 * Incluye generación, validación y extracción de información (claims).
 */
@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMinutes;

    /**
     * Constructor del servicio JWT.
     * Carga el secreto y el tiempo de expiración desde la configuración.
     *
     * @param secret            Clave secreta para firmar los tokens.
     * @param expirationMinutes Tiempo de expiración en minutos.
     */
    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-minutes}") long expirationMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    /**
     * Genera un token JWT para un usuario.
     *
     * @param username El nombre de usuario para el que se genera el token.
     * @return El token JWT firmado.
     */
    public String generateToken(String username) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationMinutes * 60);

        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    /**
     * Extrae el nombre de usuario (subject) del token JWT.
     *
     * @param token El token JWT.
     * @return El nombre de usuario contenido en el token.
     */
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Valida si un token es válido para un usuario específico.
     * Verifica que el username coincida y que el token no haya expirado.
     *
     * @param token El token JWT a validar.
     * @param user  Detalles del usuario contra el que se valida.
     * @return true si es válido, false en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica simplemente si el token tiene una estructura válida y no ha expirado
     * todavía.
     *
     * @param token El token JWT.
     * @return true si la expiración es futura.
     */
    public boolean isValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Verifica si el token ha expirado.
     *
     * @param token El token JWT.
     * @return true si ha expirado.
     */
    private boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    /**
     * Parsea y verifica los claims del token usando la clave secreta.
     *
     * @param token El token JWT.
     * @return Los claims (payload) del token.
     */
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
