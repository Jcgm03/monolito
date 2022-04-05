package com.monolitico.service;

import com.monolitico.entity.dto.PersonaDto;
import com.monolitico.entity.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaService {

    public List<Persona> listar();
    public Optional<Persona> listarId(int id);
    public Optional<Persona> listarDocumento (int documento);
    public Persona save(Persona persona);
    public Object delete(int id);
    public Persona update(Persona persona, int id);
    public List<PersonaDto> listarPersonaDto();
    public PersonaDto listarPersonaDtoById(int personaId);

}
