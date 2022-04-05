package com.monolitico.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    private String nombres;
    private String apellidos;
    private int tipo_identificacion;
    private int numero_identificacion;
    private int edad;
    private String ciudad_nacimiento;
}
