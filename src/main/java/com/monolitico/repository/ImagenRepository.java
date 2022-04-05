package com.monolitico.repository;

import com.monolitico.entity.ImagenMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ImagenRepository extends MongoRepository<ImagenMongo,String> {

    @Query("{id: '?0'}")
    ImagenMongo findIdById(String id);

    @Query("{personaId: ?0}")
    List<ImagenMongo> findAllByPersonaId(int personaId);
}
