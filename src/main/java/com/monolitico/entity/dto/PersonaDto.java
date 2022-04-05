package com.monolitico.entity.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDto {
    private int id;
    private String nombres;
    private String apellidos;
    private int tipo_identificacion;
    private int numero_identificacion;
    private int edad;
    private String ciudad_nacimiento;
    private List<ImagenDto> imagenMongo;

}
