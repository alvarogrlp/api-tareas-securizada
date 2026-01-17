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
}
