package com.clinica.laboratorio.client;

import com.clinica.laboratorio.dto.ItemExamenRequest;
import com.clinica.laboratorio.dto.PacienteOrdenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "radiologia-service", url = "${radiologia.api.url}")
public interface RadiologiaClient {
    @PostMapping("/api/radiologia/estudios/desde-solicitud")
    Object crearEstudioDesdeSolicitud(RadiologiaSolicitudRequest request);

    record RadiologiaSolicitudRequest(
            String atencionId,
            PacienteOrdenDto paciente,
            String medicoSolicitante,
            String especialidadMedico,
            ItemExamenRequest item
    ) {
    }
}
