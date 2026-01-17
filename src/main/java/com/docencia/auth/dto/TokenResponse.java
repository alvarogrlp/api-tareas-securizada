package com.docencia.auth.dto;

/**
 * DTO para la respuesta de login con el token.
 * 
 * @param token El token JWT generado.
 */
public record TokenResponse(String token) {
}
