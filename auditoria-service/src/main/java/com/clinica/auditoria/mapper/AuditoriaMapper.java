package com.clinica.auditoria.mapper;

import com.clinica.auditoria.domain.RegistroAuditoria;
import com.clinica.auditoria.dto.AuditoriaResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class AuditoriaMapper {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    public AuditoriaResponse aResponse(RegistroAuditoria r) {
        return new AuditoriaResponse(
                String.valueOf(r.getId()),
                r.getUsuarioEmail() != null ? r.getUsuarioEmail() : "—",
                r.getUsuarioNombre() != null ? r.getUsuarioNombre() : "Sistema",
                r.getRol() != null ? r.getRol() : "—",
                r.getAccion().getEtiqueta(),
                r.getModulo(),
                r.getDescripcion(),
                r.getEntidad(),
                r.getIp() != null ? r.getIp() : "—",
                r.getFecha().format(FORMATO)
        );
    }
}
