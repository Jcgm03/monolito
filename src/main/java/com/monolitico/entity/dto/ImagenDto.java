package com.monolitico.entity.dto;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;

@Getter
@Setter
public class ImagenDto {

    private String id;
    private int personaId;
    private Binary image;

}
