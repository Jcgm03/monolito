package com.monolitico.service;

import com.monolitico.entity.ImagenMongo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImagenService {

    public ImagenMongo guardImageMongo (int personaId, MultipartFile file) throws IOException;
    public Optional<ImagenMongo> obtenerImageMongo (String id);
    public Object elimiarImageMongo(String id);
    public List<ImagenMongo> listarImageMongo ();
    public Optional<ImagenMongo> updateImageMongo(String id, MultipartFile file) throws IOException;
}
