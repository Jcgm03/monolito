package com.monolitico.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monolitico.entity.ImagenMongo;
import com.monolitico.entity.Persona;
import com.monolitico.entity.dto.ImagenDto;
import com.monolitico.entity.dto.PersonaDto;
import com.monolitico.service.PersonaService;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(PersonaController.class)
class PersonaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonaService personaService;
    private Persona persona =  new Persona();
    private PersonaDto personaDto = new PersonaDto();
    List<Persona> lista = new ArrayList();
    List<PersonaDto> listaDto = new ArrayList();
    ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);

        persona.setId(14);
        persona.setNombres("juan carlos");
        persona.setApellidos("garcia mazuera");
        persona.setEdad(18);
        persona.setTipo_identificacion(1);
        persona.setNumero_identificacion(1116443913);
        persona.setCiudad_nacimiento("zarzal");

        lista.add(persona);

        personaDto.setId(14);
        personaDto.setNombres("juan carlos");
        personaDto.setApellidos("garcia mazuera");
        personaDto.setEdad(18);
        personaDto.setTipo_identificacion(1);
        personaDto.setNumero_identificacion(1116443913);
        personaDto.setCiudad_nacimiento("zarzal");

        listaDto.add(personaDto);
    }
    @Test
    void obtenerPersonas() throws Exception {
        when(personaService.listar()).thenReturn(lista);
        mvc.perform(get("/persona").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(personaService).listar();
    }

    @Test
    void guardarPersona() throws Exception {
        when(personaService.save(any())).thenReturn(persona);

        mvc.perform(post("/persona").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(persona)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombres",is("juan carlos")))
                .andExpect(jsonPath("$.apellidos",is("garcia mazuera")))
                .andExpect(jsonPath("$.tipo_identificacion",is(1)))
                .andExpect(jsonPath("$.numero_identificacion",is(1116443913)))
                .andExpect(jsonPath("$.edad",is(18)))
                .andExpect(jsonPath("$.ciudad_nacimiento",is("zarzal")));

        verify(personaService).save(any());
    }

    @Test
    void obtenerPersonaId() throws Exception {
        when(personaService.listarId(persona.getId())).thenReturn(Optional.of(persona));
        mvc.perform(get("/persona/14").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombres").value("juan carlos"))
                .andExpect(jsonPath("$.apellidos").value("garcia mazuera"));

        verify(personaService).listarId(persona.getId());

    }

    @Test
    void updatePersona() throws Exception {

        when(personaService.update(persona, persona.getId())).thenReturn(persona);

        persona.setNombres("Sebastian");
        persona.setApellidos("mazuera");
        persona.setEdad(20);
        persona.setTipo_identificacion(1);
        persona.setNumero_identificacion(1234567);
        persona.setCiudad_nacimiento("zarzal");

        mvc.perform(put("/persona/14").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(persona)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.nombres").value("Sebastian"))
                .andExpect(jsonPath("$.apellidos").value("mazuera"))
                .andExpect(jsonPath("$.tipo_identificacion").value(1))
                .andExpect(jsonPath("$.numero_identificacion").value(1234567))
                .andExpect(jsonPath("$.edad").value(20))
                .andExpect(jsonPath("$.ciudad_nacimiento").value("zarzal"));

        verify(personaService).update(persona, persona.getId());

    }

    @Test
    void eliminarPersona() throws Exception {
        when(personaService.delete(persona.getId())).thenReturn(Optional.of(persona));
        mvc.perform(delete("/persona/14").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(personaService).delete(persona.getId());

    }

    @Test
    void listarTodo() throws Exception {
        when(personaService.listarPersonaDto()).thenReturn(listaDto);
        mvc.perform(get("/persona/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(personaService).listarPersonaDto();
    }

    @Test
    void listarTodoById() throws Exception {
        when(personaService.listarPersonaDtoById(personaDto.getId())).thenReturn(personaDto);
        mvc.perform(get("/persona/all/14").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombres").value("juan carlos"))
                .andExpect(jsonPath("$.apellidos").value("garcia mazuera"));
        verify(personaService).listarPersonaDtoById(personaDto.getId());
    }
}