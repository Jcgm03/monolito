package com.monolitico.controller;

import com.monolitico.entity.ImagenMongo;
import com.monolitico.service.ImagenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persona")
public class ImagenController {
    @Autowired
    private ImagenService imageService;

    @Operation(summary = "Listar imagenes en mongo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagenes listadas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImagenMongo.class)) })})
   @GetMapping("/img")
    public ResponseEntity<List<ImagenMongo>> listar(){
       return ResponseEntity.status(HttpStatus.OK).body(imageService.listarImageMongo());
   }

    @Operation(summary = "Guardar imagen en mongo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen guardada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImagenMongo.class)) }),
            @ApiResponse(responseCode = "400", description = "Imagen no guardada",
                    content = @Content) })
   @PostMapping(value = "/img",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ImagenMongo> guardarImagen(
            @RequestParam("userId") int personaId,
            @RequestParam("img") MultipartFile file) throws Exception {
       return ResponseEntity.status(HttpStatus.CREATED).body(imageService.guardImageMongo(personaId,file));
   }

    @Operation(summary = "Buscar imagen en mongo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen encontrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImagenMongo.class)) }),
            @ApiResponse(responseCode = "400", description = "Imagen no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontro la imagen",
                    content = @Content) })

   @GetMapping("/img/{id}")
    public ResponseEntity<Optional<ImagenMongo>> buscarImagen (@PathVariable String id){
       return ResponseEntity.status(HttpStatus.OK).body(imageService.obtenerImageMongo(id));
   }

    @Operation(summary = "Eliminar imagen en mongo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen eliminada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImagenMongo.class)) }),
            @ApiResponse(responseCode = "400", description = "Imagen no se puedo eliminar",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontro la imagen",
                    content = @Content) })
   @DeleteMapping("/img/{id}")
    public ResponseEntity<?> eliminarImagen (@PathVariable String id){
       imageService.elimiarImageMongo(id);
       return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar imagen en mongo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen actualizada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImagenMongo.class)) }),
            @ApiResponse(responseCode = "400", description = "Imagen no se puedo actualizar",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encontro la imagen",
                    content = @Content) })

   @PutMapping(value = "/img/{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Optional<ImagenMongo>> updateImagenMongo (
            @PathVariable("id") String id,
            @RequestParam("img") MultipartFile file) throws Exception{
       return ResponseEntity.status(HttpStatus.OK).body(imageService.updateImageMongo(id,file));
   }
}
