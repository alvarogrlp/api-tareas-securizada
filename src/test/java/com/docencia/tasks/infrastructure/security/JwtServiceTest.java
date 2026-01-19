package com.docencia.tasks.infrastructure.security;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias para JwtService.
 */
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;
    private final String secret = "Kraj8AxPPe5XdByv9wN4o4cwhW8ExUoxH3kGIG9oY3MobGgN7zbPmmG2aomaZ7RP6EH17Le6RdX6+k0DPxqbfQ==";
    private final long expiration = 60; // 60 minutes

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(secret, expiration);
    }

    @Test
    void testGenerateToken() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testExtractUsername() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        String extracted = jwtService.extractUsername(token);
        assertEquals(username, extracted);
    }

    @Test
    void testIsTokenValid() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        when(userDetails.getUsername()).thenReturn(username);

        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void testIsTokenInvalidUsername() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        when(userDetails.getUsername()).thenReturn("otheruser");

        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertFalse(isValid);
    }

    @Test
    void testIsValidSimple() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        boolean isValid = jwtService.isValid(token);
        assertTrue(isValid);
    }

    @Test
    void testIsValidSimpleInvalid() {
        String token = "invalid.token.string";
        boolean isValid = jwtService.isValid(token);
        assertFalse(isValid);
    }

    @Test
    void testExpiredToken() throws InterruptedException {
        // Create service with 1 second expiration (represented as close to 0 minutes
        // for the test if possible,
        // but the service takes long minutes.
        // Actually the service property is expirationMinutes.
        // If I pass 0 or a very small float converted to long, it might be 0 minutes.
        // Let's rely on creating a token manually that is expired.

        // We can't easy inject a "past" date into generateToken without changing the
        // service code.
        // But we can create a token using Jwts directly in the test ensuring it is
        // expired.

        java.util.Map<String, Object> claims = new java.util.HashMap<>();
        String expiredToken = Jwts.builder()
                .subject("user")
                .expiration(new java.util.Date(System.currentTimeMillis() - 1000 * 60)) // Expired 1 min ago
                .signWith(io.jsonwebtoken.security.Keys
                        .hmacShaKeyFor(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8)))
                .compact();

        boolean isValid = jwtService.isValid(expiredToken);
        assertFalse(isValid);

        // Also checks exception handling in isTokenExpired
        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> jwtService.extractUsername(expiredToken));
    }
}
