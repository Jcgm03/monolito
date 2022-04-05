package com.monolitico.service.serviceImp;

import com.monolitico.config.exception.NotFoundException;
import com.monolitico.entity.ImagenMongo;
import com.monolitico.entity.Persona;
import com.monolitico.entity.dto.ImagenDto;
import com.monolitico.entity.dto.PersonaDto;
import com.monolitico.mapper.ImagenMapper;
import com.monolitico.mapper.PersonaMapper;
import com.monolitico.repository.ImagenRepository;
import com.monolitico.repository.PersonaRepository;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PersonaServiceImpTest {

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private PersonaMapper personaMapper;

    @Mock
    private ImagenMapper imagenMapper;

    @Mock
    private ImagenRepository imagenRepository;

    @InjectMocks
    private PersonaServiceImp personaServiceImp;
    private Persona persona;
    private PersonaDto personaDto;
    private ImagenMongo imagenMongo;
    private ImagenDto imagenDto;

    List<Persona> lista = new ArrayList();
    List<PersonaDto> listaDto = new ArrayList();
    List<ImagenMongo> listaImagenMongo = new ArrayList();
    List<ImagenDto> listaImagenDto = new ArrayList();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        persona = new Persona();
        persona.setId(14);
        persona.setNombres("juan carlos");
        persona.setApellidos("garcia mazuera");
        persona.setEdad(18);
        persona.setTipo_identificacion(1);
        persona.setNumero_identificacion(1116443913);
        persona.setCiudad_nacimiento("zarzal");

        lista.add(persona);

        personaDto = new PersonaDto();
        personaDto.setId(14);
        personaDto.setNombres("juan carlos");
        personaDto.setApellidos("garcia mazuera");
        personaDto.setEdad(18);
        personaDto.setTipo_identificacion(1);
        personaDto.setNumero_identificacion(1116443913);
        personaDto.setCiudad_nacimiento("zarzal");

        listaDto.add(personaDto);

        imagenMongo = new ImagenMongo();
        imagenMongo.setId("622f93aee29bf43fcfafafd3");
        imagenMongo.setPersonaId(14);
        imagenMongo.setImage(new Binary(new byte[] { (byte)0xe0 }));

        listaImagenMongo.add(imagenMongo);

        imagenDto = new ImagenDto();
        imagenDto.setId("622f93aee29bf43fcfafafd3");
        imagenDto.setPersonaId(14);
        imagenDto.setImage(new Binary(new byte[] { (byte)0xe0 }));

        listaImagenDto.add(imagenDto);
    }

    @Test
    void listar() {

        when(personaRepository.findAll()).thenReturn(Arrays.asList(persona));

        assertNotNull(personaServiceImp.listar());
    }

    @Test
    void listarId() {
        when(personaRepository.findById(persona.getId())).thenReturn(Optional.of(persona));
        assertNotNull(personaServiceImp.listarId(persona.getId()));
    }

    @Test
    void cuandoElListarIdNoEncuentraLaPersona() {

        when(personaRepository.findById((20))).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            personaServiceImp.listarId(20);

        });
        assertTrue("La persona no existe".contains(exception.getMessage()));
    }

    @Test
    void listarDocumento() {
        when(personaRepository.findByDocumento(persona.getNumero_identificacion())).thenReturn(Optional.of(persona));
        assertNotNull(personaServiceImp.listarDocumento(persona.getNumero_identificacion()));
    }

    @Test
    void cuandoElistarDocumentoNoEncuentraLaPersona() {

        when(personaRepository.findByDocumento((1542))).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            personaServiceImp.listarDocumento(1542);

        });
        assertTrue("La persona no existe".contains(exception.getMessage()));
    }

    @Test
    void save() {
        when(personaRepository.save(any(Persona.class))).thenReturn(persona);
        assertNotNull(personaServiceImp.save(persona));
    }

    @Test
    void delete() {
       when(personaRepository.findById(persona.getId())).thenReturn(Optional.of(persona));
        assertNull(personaServiceImp.delete(persona.getId()));
    }

    @Test
    void cuandoElDeleteNoEncuentraLaPersona() {

        when(personaRepository.findById(persona.getId())).thenReturn(Optional.of(persona));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            personaServiceImp.delete(20);
        });
        assertTrue("La persona no existe".contains(exception.getMessage()));
    }
    @Test
    void update() {
        when(personaRepository.findById(persona.getId())).thenReturn(Optional.of(persona));
        Optional<Persona> datos = personaRepository.findById(persona.getId());
        assertNull(personaServiceImp.update(new Persona(), persona.getId()));
        assertTrue(datos.isPresent());
    }

    @Test
    void cuandoElupdateNoEncuentraLaPersona() {

        when(personaRepository.findById(persona.getId())).thenReturn(Optional.of(persona));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            personaServiceImp.update(persona,20);
        });
        assertTrue("La persona no existe".contains(exception.getMessage()));
    }

    @Test
    void listaPersona() {
        when(personaRepository.findAll()).thenReturn(lista);
        when(personaMapper.personaDtoList(lista)).thenReturn(listaDto);
        when(imagenRepository.findAllByPersonaId(any(Integer.class))).thenReturn(listaImagenMongo);
        when(imagenMapper.toListImagenDto(listaImagenMongo)).thenReturn(listaImagenDto);
        listaDto.get(0).setImagenMongo(listaImagenDto);
        assertArrayEquals(listaImagenDto.get(0).getImage().getData(),
                personaServiceImp.listarPersonaDto().get(0).getImagenMongo().get(0).getImage().getData());
    }

    @Test
    void listaPersonaMongo(){
        when(personaRepository.findById(persona.getId())).thenReturn(Optional.of(persona));
        when(personaMapper.ToPersonaDto(persona)).thenReturn(personaDto);
        when(imagenRepository.findAllByPersonaId(any(Integer.class))).thenReturn(listaImagenMongo);
        when(imagenMapper.toListImagenDto(listaImagenMongo)).thenReturn(listaImagenDto);
        listaDto.get(0).setImagenMongo(listaImagenDto);
        assertArrayEquals(listaImagenDto.get(0).getImage().getData(),
                personaServiceImp.listarPersonaDtoById(persona.getId()).getImagenMongo().get(0).getImage().getData());

    }



}