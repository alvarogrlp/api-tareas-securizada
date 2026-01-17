package com.docencia.auth.controller;

import com.docencia.auth.dto.LoginRequest;
import com.docencia.auth.dto.TokenResponse;
import com.docencia.auth.service.AuthService;
import com.docencia.tasks.infrastructure.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controlador REST para la autenticación.
 * Expone endpoints para el inicio de sesión.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * Constructor del controlador con inyección de dependencias.
     *
     * @param authService Servicio de validación de credenciales.
     * @param jwtService  Servicio de gestión de JWT.
     */
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    /**
     * Endpoint para realizar login.
     *
     * @param req Objeto DTO con usuario y contraseña.
     * @return TokenResponse con el JWT generado si las credenciales son válidas.
     * @throws ResponseStatusException 401 Unauthorized si las credenciales son
     *                                 inválidas.
     */
    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest req) {
        if (!authService.validateCredentials(req.username(), req.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        return new TokenResponse(jwtService.generateToken(req.username()));
    }
}
