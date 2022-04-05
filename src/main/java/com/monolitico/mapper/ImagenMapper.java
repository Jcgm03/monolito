package com.monolitico.mapper;

import com.monolitico.entity.dto.ImagenDto;
import com.monolitico.entity.ImagenMongo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImagenMapper {

    ImagenDto toImagenDto (ImagenMongo imagenMongo);

    List<ImagenDto> toListImagenDto (List<ImagenMongo> imagenMongo);


}
