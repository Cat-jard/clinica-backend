package com.historiaclinica.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecetaDTO {

    private String id;
    private String estado;
    private List<ItemRecetaDTO> items;
}
