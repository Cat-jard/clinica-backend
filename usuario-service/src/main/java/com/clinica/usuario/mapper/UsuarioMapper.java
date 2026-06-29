package com.clinica.usuario.mapper;

import com.clinica.usuario.domain.Usuario;
import com.clinica.usuario.dto.UsuarioResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class UsuarioMapper {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    public UsuarioResponse aResponse(Usuario u) {
        return new UsuarioResponse(
                String.valueOf(u.getId()),
                u.getDni(),
                u.getNombre(),
                u.getApellidos(),
                u.getEmail(),
                u.getTelefono(),
                u.getRol().getEtiqueta(),
                u.getEspecialidad(),
                u.getEstado().getEtiqueta(),
                u.getUltimoAcceso() != null ? u.getUltimoAcceso().format(FORMATO) : "—",
                u.getRol().getRuta()
        );
    }
}
