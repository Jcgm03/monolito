package com.monolitico.controller;

import com.monolitico.entity.dto.PersonaDto;
import com.monolitico.entity.Persona;
import com.monolitico.service.PersonaService;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private PersonaService personaService;


    @Operation(summary = "Buscar personas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona listadas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class)) })})
    @GetMapping
    public List<Persona> obtenerPersonas(){
        return personaService.listar();
    }

    @Operation(summary = "Guardar persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona guardada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class)) }),
            @ApiResponse(responseCode = "400", description = "Persona no guardada",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<Persona> guardarPersona(@RequestBody Persona persona) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personaService.save(persona));
    }
    @Operation(summary = "Obtener personas por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class)) }),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Persona>> obtenerPersonaId(@PathVariable("id") int id){
        return  ResponseEntity.status(HttpStatus.OK).body(personaService.listarId(id));
    }
    @Operation(summary = "Actualzar personas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona actualizada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class)) }),
            @ApiResponse(responseCode = "400", description = "Persona no actualizada",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada",
                    content = @Content)})
    @PutMapping(value = "/{id}",  produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Persona> updatePersona(@RequestBody Persona persona,@PathVariable("id") int id){
      return ResponseEntity.status(HttpStatus.OK).body(personaService.update(persona,id));
    }
    @Operation(summary = "Eliminar personas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona eliminada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class)) }),
            @ApiResponse(responseCode = "400", description = "Persona no se elimino",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPersona(@PathVariable("id") int id){
       personaService.delete(id);
       return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Obtener personas con imagen en mongo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personas obtenidas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class)) })})
    @GetMapping(path = "/all")
    public ResponseEntity<List<PersonaDto>> listarTodo(){
        return ResponseEntity.status(HttpStatus.OK).body(personaService.listarPersonaDto());
    }

    @Operation(summary = "Obtener personas con imagen en mongo por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona obtenidas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Persona.class)) }),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada",
                    content = @Content)})
    @GetMapping(path = "/all/{id}")
    public ResponseEntity<PersonaDto> listarTodoById(@PathVariable("id") int id){
        return ResponseEntity.status(HttpStatus.OK).body(personaService.listarPersonaDtoById(id));
    }

}

