package com.monolitico.entity;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "imagenMongo")
public class ImagenMongo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private int personaId;
    private Binary image;

    public ImagenMongo(int personaId) {
        this.personaId = personaId;
    }
    public ImagenMongo() {
    }

}
