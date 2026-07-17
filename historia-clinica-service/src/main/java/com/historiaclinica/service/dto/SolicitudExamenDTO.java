package com.historiaclinica.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SolicitudExamenDTO {

    private String id;
    private String estado;
    private List<ItemExamenDTO> items;
}
