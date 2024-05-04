package com.obbutcheryproyecto.controller;

import com.obbutcheryproyecto.dto.UserProjection;
import com.obbutcheryproyecto.dto.VatProjection;
import com.obbutcheryproyecto.dto.VatRepositoryDto;
import com.obbutcheryproyecto.entities.Vat;
import com.obbutcheryproyecto.repository.VatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VatController {
    @Autowired
    private final VatRepository vatRepository;
    private final VatRepositoryDto vatRepositoryDto;

    public VatController(VatRepository vatRepository, VatRepositoryDto vatRepositoryDto){
        this.vatRepository = vatRepository;
        this.vatRepositoryDto = vatRepositoryDto;
    }

    /**
     * Devuelve una lista con todos los impuestos y sus asociaciones mediante el método GET
     * @return ListaImpuestos
     */
    @GetMapping("/api/vats")
    private List<Vat> findAll(){
        return this.vatRepository.findAll();
    }

    /**
     * Devuelve una lista con todos los usuarios omitiendo sus asociaciones mediante el método GET
     * @return ListaUsuarios
     */
    @GetMapping("/api/vats/dto")
    private List<VatProjection> findAllDto(){
        return this.vatRepositoryDto.findAllDto();
    }

    /**
     * Crea un nuevo impuesto mediante método POST
     * @param vat
     * @return Impuesto guardado o mensaje de error
     */
    @PostMapping("/api/vat")
    private ResponseEntity<?> create(@RequestBody Vat vat) { //@RequestBody hace que los datos a guardar se almacenen en el cuerpo de la query
        try {
            Vat createdVat = this.vatRepository.save(vat);
            return ResponseEntity.ok(createdVat); // Devuelve la etiqueta creada si la creación fue exitosa
        } catch (Exception e) {
            // Si ocurre algún error durante la creación, devuelve una respuesta de error con el mensaje de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el impuesto: " + e.getMessage());
        }
    }


    /**
     * Modifica un impuesto existente
     * @param vat
     * @return Impuesto modificado o mensaje de error
     */
    @PutMapping("/api/vat")
    private ResponseEntity<?> update(@RequestBody Vat vat) { //@RequestBody hace que los datos a guardar se almacenen en el cuerpo de la query
        if (this.vatRepository.existsById(vat.getId())) {
            try {
                Vat updatedVat = this.vatRepository.save(vat);
                return ResponseEntity.ok(updatedVat); // Devuelve el impuesto modificado si la actualización fue exitosa
            } catch (Exception e) {
                // Si ocurre algún error durante la actualización, devuelve una respuesta de error con el mensaje de la excepción
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el impuesto: " + e.getMessage());
            }
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El impuesto con id = " + vat.getId() + " no existe!");
    }

    /* **** MÉTODO DELETE ANTERIOR
    @DeleteMapping("/api/tag/{id}")
    //@PathVariable define una variable como parámetro para ser usado en un método de repository
    private String delete(@PathVariable Long id){
        //Comprobamos si el impuesto existe
        if (this.vatRepository.existsById(id)) {
            this.vatRepository.deleteById(id);
            return "Impuesto " + id + " eliminado";
        } else return "El impuesto " + id + " no existe. No se ha borrado ningún registro";
    }
    */

    // TODO revisar integridad referencial (Ojo con las asociaciones...CASCADE, etc)
    /**
     * Elimina un impuesto que coincide con la id dada
     * @param id Long
     * @return ResponseEntity
     */
    @DeleteMapping("/api/vat/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        // Si el impuesto existe y está asociado a algún artículo, impedimos su borrado.(Integridad referencial)
        if (vatRepository.existsById(id) & !vatRepository.getById(id).getArticles().isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al eliminar el impuesto: El impuesto esta asociado al menos a un artículo");
        if (vatRepository.existsById(id)) {
            try {
                vatRepository.deleteById(id);
                return ResponseEntity.ok("Impuesto " + id +" eliminado con éxito");
            } catch (Exception e) {
                // Si ocurre algún error durante la eliminación, devuelve una respuesta de error con el mensaje de la excepción
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el impuesto: " + e.getMessage());
            }
        } else {
            // Si la etiqueta no existe, devuelve una respuesta de error con estado 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El impuesto con id = " + id + " no existe");
        }
    }
}
