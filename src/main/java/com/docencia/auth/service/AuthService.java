package com.docencia.auth.service;

import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación simple.
 * Valida las credenciales del usuario.
 */
@Service
public class AuthService {

    /**
     * Valida si las credenciales son correctas.
     * Implementación simple para demostración.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return true si las credenciales coinciden, false en caso contrario.
     */
    public boolean validateCredentials(String username, String password) {
        // En un caso real, esto comprobaría contra BDD o usaría AuthenticationManager
        return "user".equals(username) && "pass".equals(password);
    }
}
