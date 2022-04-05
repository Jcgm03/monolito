package com.monolitico.service.serviceImp;

import com.monolitico.config.exception.NotFoundException;
import com.monolitico.entity.ImagenMongo;
import com.monolitico.repository.ImagenRepository;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ImagenServiceImpTest {

    @Mock
    private ImagenRepository imagenRepository;

    @InjectMocks
    private ImagenServiceImp imagenServiceImp;
    private ImagenMongo imagenMongo;

    private MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
            "text/plain", "Spring Framework".getBytes());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imagenMongo = new ImagenMongo();
        imagenMongo.setImage(new Binary(new byte[] { (byte)0xe0 }));
        imagenMongo.setId("622f93aee29bf43fcfafafd3");
        imagenMongo.setPersonaId(14);
    }

    @Test
    void guardImageMongo() throws IOException {
        when(imagenServiceImp.guardImageMongo(imagenMongo.getPersonaId(),multipartFile)).thenReturn(imagenMongo);
        assertNotNull(imagenServiceImp.guardImageMongo(imagenMongo.getPersonaId(), multipartFile));
    }

    @Test
    void obtenerImageMongo() {
        when(imagenRepository.findIdById(imagenMongo.getId())).thenReturn(imagenMongo);
        assertNotNull(imagenServiceImp.obtenerImageMongo(imagenMongo.getId()));
    }

    @Test
    void elimiarImageMongo() {
        when(imagenRepository.findById(imagenMongo.getId())).thenReturn(Optional.of(imagenMongo));
       assertNull(imagenServiceImp.elimiarImageMongo(imagenMongo.getId()));
    }

    @Test
    void listarImageMongo() {
        when(imagenRepository.findAll()).thenReturn(Arrays.asList(imagenMongo));
        assertNotNull(imagenServiceImp.listarImageMongo());
    }

    @Test
    void updateImageMongo() {
        when(imagenRepository.findById(imagenMongo.getId())).thenReturn(Optional.of(imagenMongo));
        when(imagenServiceImp.updateImageMongo(imagenMongo.getId(), multipartFile)).thenReturn(Optional.of(imagenMongo));
        assertNull(imagenServiceImp.elimiarImageMongo(imagenMongo.getId()));
    }
}