package com.monolitico.service.serviceImp;

import com.monolitico.config.exception.BadRequestException;
import com.monolitico.config.exception.NotFoundException;
import com.monolitico.entity.ImagenMongo;
import com.monolitico.repository.ImagenRepository;
import com.monolitico.service.ImagenService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImagenServiceImp implements ImagenService {
    @Autowired
    private ImagenRepository imagenRepository;

    @Override
    public ImagenMongo guardImageMongo(int personaId, MultipartFile file) throws IOException {
       ImagenMongo imageMongoGuardar = new ImagenMongo(personaId);
        imageMongoGuardar.setImage( new Binary(BsonBinarySubType.BINARY,file.getBytes()));
       imagenRepository.insert(imageMongoGuardar);
       return imageMongoGuardar;
    }

    @Override
    public Optional<ImagenMongo> obtenerImageMongo(String id) {
        return Optional.ofNullable(imagenRepository.findIdById(id));
    }

    @Override
    public Object elimiarImageMongo(String id) {
       Optional<ImagenMongo> imageMongoEliminar =  imagenRepository.findById(id);
       if (!imageMongoEliminar.isPresent()) {
           throw new NotFoundException("El id de la imagen no existe");
       }
       imagenRepository.delete(imageMongoEliminar.get());
        return null;
    }

    @Override
    public List<ImagenMongo> listarImageMongo() {
        return imagenRepository.findAll();
    }

    @Override
    public Optional<ImagenMongo> updateImageMongo(String id, MultipartFile file) {
        Optional<ImagenMongo> imageMongoUpdate = imagenRepository.findById(id);
        if (!imageMongoUpdate.isPresent()){
            throw new NotFoundException("El usuario no existe");
        }
        try {
            imageMongoUpdate.get().setImage(new Binary(BsonBinarySubType.BINARY,file.getBytes()));
        } catch (Exception e){
            throw new BadRequestException("No se puedo elimar la image");
        }
        imagenRepository.save(imageMongoUpdate.get());
        return null;
    }
}
