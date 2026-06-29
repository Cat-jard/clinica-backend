package com.clinica.usuario.dto;

/**
 * Respuesta del login: token JWT y datos del usuario autenticado. El campo
 * {@code ruta} permite al frontend redirigir al modulo correspondiente al rol.
 */
public record LoginResponse(
        String token,
        String tipoToken,      // "Bearer"
        long expiraEnMs,
        String ruta,
        UsuarioResponse usuario
) {
    public static LoginResponse de(String token, long expiraEnMs, UsuarioResponse usuario) {
        return new LoginResponse(token, "Bearer", expiraEnMs, usuario.ruta(), usuario);
    }
}
