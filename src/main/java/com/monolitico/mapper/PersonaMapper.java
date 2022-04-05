package com.monolitico.mapper;

import com.monolitico.entity.dto.PersonaDto;
import com.monolitico.entity.Persona;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

    PersonaDto ToPersonaDto(Persona Persona);
    Persona ToDtoPersona(PersonaDto personaDto);

    List<PersonaDto> personaDtoList (List<Persona> personas );

}
