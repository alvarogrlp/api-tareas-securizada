package com.docencia.tasks.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Test
    void testSecurityBeansConfigured() {
        assertThat(securityFilterChain).isNotNull();
        assertThat(passwordEncoder).isNotNull();
        assertThat(authenticationManager).isNotNull();
        assertThat(corsConfigurationSource).isNotNull();
    }
}
