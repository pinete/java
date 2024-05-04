package com.obbutcheryproyecto.controller;

import com.obbutcheryproyecto.entities.Tag;
import com.obbutcheryproyecto.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TagController {
    @Autowired
    private final TagRepository tagRepository;

    public  TagController(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    /**
     * Devuelve una lista con todas las etiquetas y sus asociaciones mediante el método GET
     * @return ListaEtiquetas
     */
    @GetMapping("/api/tags")
    private List<Tag> findAll(){
        return this.tagRepository.findAll();
    }

    /**
     * Crea una nueva etiqueta mediante método POST
     * @param tag
     * @return Etiqueta guardada o mensaje de error
     */
    @PostMapping("/api/tags")
    private ResponseEntity<?> create(@RequestBody Tag tag) { //@RequestBody hace que los datos a guardar se almacenen en el cuerpo de la query
        try {
            Tag createdTag = this.tagRepository.save(tag);
            return ResponseEntity.ok(createdTag); // Devuelve la etiqueta creada si la creación fue exitosa
        } catch (Exception e) {
            // Si ocurre algún error durante la creación, devuelve una respuesta de error con el mensaje de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la etiqueta: " + e.getMessage());
        }
    }


    /**
     * Modifica una etiqueta existente
     * @param tag
     * @return Etiqueta modificada o mensaje de error
     */
    @PutMapping("/api/tag")
    private ResponseEntity<?> update(@RequestBody Tag tag) { //@RequestBody hace que los datos a guardar se almacenen en el cuerpo de la query
        if (this.tagRepository.existsById(tag.getId())) {
            try {
                Tag updatedTag = this.tagRepository.save(tag);
                return ResponseEntity.ok(updatedTag); // Devuelve la etiqueta modificada si la actualización fue exitosa
            } catch (Exception e) {
                // Si ocurre algún error durante la actualización, devuelve una respuesta de error con el mensaje de la excepción
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la etiqueta: " + e.getMessage());
            }
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La etiqueta con id = " + tag.getId() + " no existe!");
    }

    /* **** MÉTODO DELETE ANTERIOR
    @DeleteMapping("/api/tag/{id}")
    //@PathVariable define una variable como parámetro para ser usado en un método de repository
    private String delete(@PathVariable Long id){
        //Comprobamos si la tarea existe
        if (this.tagRepository.existsById(id)) {
            this.tagRepository.deleteById(id);
            return "Etiqueta " + id + " eliminada";
        } else return "La etiqueta " + id + " no existe. No se ha borrado ningún registro";
    }
    */

    // TODO revisar integridad referencial (Ojo con las asociaciones...CASCADE, etc)
    /**
     * Elimina una etiqueta que coincide con la id dada
     * @param id Long
     * @return ResponseEntity
     */
    @DeleteMapping("/api/tag/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        if (tagRepository.existsById(id)) {
            try {
                tagRepository.deleteById(id);
                return ResponseEntity.ok("Etiqueta " + id +" eliminada con éxito");
            } catch (Exception e) {
                // Si ocurre algún error durante la eliminación, devuelve una respuesta de error con el mensaje de la excepción
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la etiqueta: " + e.getMessage());
            }
        } else {
            // Si la etiqueta no existe, devuelve una respuesta de error con estado 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La etiqueta con id = " + id + " no existe");
        }
    }
}
