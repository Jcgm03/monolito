package com.monolitico.service.serviceImp;

import com.monolitico.config.exception.BadRequestException;
import com.monolitico.config.exception.NotFoundException;
import com.monolitico.entity.dto.PersonaDto;
import com.monolitico.entity.ImagenMongo;
import com.monolitico.entity.Persona;
import com.monolitico.mapper.ImagenMapper;
import com.monolitico.mapper.PersonaMapper;
import com.monolitico.repository.ImagenRepository;
import com.monolitico.repository.PersonaRepository;
import com.monolitico.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImp implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ImagenRepository imagenRepository;

    @Autowired
    private PersonaMapper personaMapper;

    @Autowired
    private ImagenMapper imagenMapper;

    @Override
    public List<Persona> listar() {
        List<Persona> all = personaRepository.findAll();
        return all;
    }

    @Override
    public Optional<Persona> listarId(int id) {
        Optional<Persona> Opers = personaRepository.findById(id);
        if (!Opers.isPresent()) {
           throw new NotFoundException("La persona no existe");
        }
        return personaRepository.findById(id);
    }

    @Override
    public Optional<Persona> listarDocumento(int documento) {
        Optional<Persona> byDocumento = personaRepository.findByDocumento(documento);
        if (!byDocumento.isPresent()) {
            throw new NotFoundException("La persona no existe");
        }
        return byDocumento;
    }

    @Override
    public Persona save(Persona persona) {
        if (persona.getEdad() <18){
            throw new BadRequestException("El usuario es menor de edad");
        }
        return personaRepository.save(persona);
    }

    @Override
    public Object delete(int id) {
       personaRepository.findById(id).orElseThrow(()
                            -> new NotFoundException("La persona no existe"));

       personaRepository.deleteById(id);
        return null;
    }

    @Override
    public Persona update(Persona persona, int id) {
        Optional<Persona> oPersonaUpdate = personaRepository.findById(id);
        if (!oPersonaUpdate.isPresent()){
            throw new NotFoundException("La persona no existe");
        }
        oPersonaUpdate.get().setEdad(persona.getEdad());
        oPersonaUpdate.get().setNumero_identificacion(persona.getNumero_identificacion());
        oPersonaUpdate.get().setApellidos(persona.getApellidos());
        oPersonaUpdate.get().setNombres(persona.getNombres());
        oPersonaUpdate.get().setTipo_identificacion(persona.getTipo_identificacion());
        oPersonaUpdate.get().setCiudad_nacimiento(persona.getCiudad_nacimiento());
       return personaRepository.save(oPersonaUpdate.get());
    }

    @Override
    public List<PersonaDto> listarPersonaDto() {
       List<Persona> listarPersona = personaRepository.findAll();
       List<PersonaDto> personaDtoList = personaMapper.personaDtoList(listarPersona);
       personaDtoList.stream().forEach(per -> {
           List<ImagenMongo> imagenMongo = imagenRepository.findAllByPersonaId(per.getId());
           per.setImagenMongo(imagenMapper.toListImagenDto(imagenMongo));
       });
    return personaDtoList;
    }

    @Override
    public PersonaDto listarPersonaDtoById(int personaId) {
        Optional<Persona> persona = personaRepository.findById(personaId);

        if (!persona.isPresent()) {
            throw new NotFoundException("La persona no existe");
        }
        PersonaDto personaDto = personaMapper.ToPersonaDto(persona.get());
        List<ImagenMongo> imagenMongo = imagenRepository.findAllByPersonaId(personaDto.getId());
        personaDto.setImagenMongo(imagenMapper.toListImagenDto(imagenMongo));
        return personaDto;
    }

}
