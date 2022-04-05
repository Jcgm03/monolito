package com.monolitico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monolitico.entity.ImagenMongo;
import com.monolitico.service.ImagenService;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.test.web.servlet.ResultMatcher.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ImagenController.class)
class ImagenControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ImagenService imagenService;
    ObjectMapper objectMapper;
    private ImagenMongo imagenMongo;
    List<ImagenMongo> listaImagenMongo = new ArrayList();

    private MockMultipartFile multipartFile = new MockMultipartFile("img", "test.txt",
            "text/plain", "Spring Framework".getBytes());

    MockMultipartHttpServletRequestBuilder builder =
            MockMvcRequestBuilders.multipart("/persona/img/622f93aee29bf43fcfafafd3");


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);

        imagenMongo = new ImagenMongo();
        imagenMongo.setImage(new Binary(new byte[] { (byte)0xe0 }));
        imagenMongo.setId("622f93aee29bf43fcfafafd3");
        imagenMongo.setPersonaId(14);

        listaImagenMongo.add(imagenMongo);

    }


    @Test
    void listar() throws Exception {
        when(imagenService.listarImageMongo()).thenReturn(listaImagenMongo);
        mvc.perform(get("/persona/img").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(imagenService).listarImageMongo();
    }

   @Test
   void guardarImagen() throws Exception {
       when(imagenService.guardImageMongo(imagenMongo.getPersonaId(),multipartFile)).thenReturn(imagenMongo);
       mvc.perform(multipart("/persona/img")
               .file(multipartFile)
               .param("userId","14"))
               .andExpect(status().isCreated());
       verify(imagenService).guardImageMongo(imagenMongo.getPersonaId(),multipartFile);
   }

    @Test
    void buscarImagen() throws Exception {
        when(imagenService.obtenerImageMongo(imagenMongo.getId())).thenReturn(Optional.of(imagenMongo));
        mvc.perform(get("/persona/img/622f93aee29bf43fcfafafd3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.personaId").value(14));

        verify(imagenService).obtenerImageMongo(imagenMongo.getId());
    }

    @Test
    void eliminarImagen() throws Exception {
        when(imagenService.elimiarImageMongo(imagenMongo.getId())).thenReturn(imagenMongo);
        mvc.perform(delete("/persona/img/622f93aee29bf43fcfafafd3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(imagenService).elimiarImageMongo(imagenMongo.getId());
    }

    @Test
    void updateImagenMongo() throws Exception {
       builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });
        mvc.perform(builder
                        .file(multipartFile))
                .andExpect(status().isOk());
        verify(imagenService).updateImageMongo(imagenMongo.getId(),multipartFile);
    }
}