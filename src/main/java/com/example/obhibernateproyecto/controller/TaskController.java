package com.example.obhibernateproyecto.controller;

import com.example.obhibernateproyecto.entities.Task;
import com.example.obhibernateproyecto.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
public class TaskController {

    private final TaskRepository taskRepository;

    public  TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    /**
     * Devuelve una lista con todas las tareas y sus asociaciones mediante el método GET
     * @return ListaTareas
     */
    @GetMapping("/api/tasks")
    private List<Task> findAll(){
        return this.taskRepository.findAll();
    }

    /**
     *  Devuelve una lista con todas las tareas y sus asociaciones que tienen el campo deliveryDate entre las fechas dadas, mediante el método GET
     * @param startDeliveryDate
     * @param endDeliveryDate
     * @return ListaTareas
     */
    //NOTA: Spring reconoce el nombre del campo y la palabra BETWEEN en el nombre del método y automáticamente realiza la query con between.
    //      Cuando llamas al método findByDeliveryDateBetween(startDeliveryDate, endDeliveryDate) en el repositorio, Spring Data JPA traduce
    //      esto a una consulta SQL similar a:
    //          SELECT * FROM Task WHERE deliveryDate BETWEEN startDeliveryDate AND endDeliveryDate;
    @GetMapping("/api/tasks/betweendates")
    private List<Task> findAllBetweenDeliveryDates( @RequestParam("startDeliveryDate") String startDeliveryDate,
                                                    @RequestParam("endDeliveryDate") String endDeliveryDate){
        return this.taskRepository.findByDeliveryDateBetween(LocalDate.parse(startDeliveryDate),LocalDate.parse(endDeliveryDate));
    }

    /**
     * Crea un nuevo usuario mediante método POST
     * @param task
     * @return Tarea guardada o mensaje de error
     */

    /* Opcion Sencilla
    @PostMapping("/api/task")
    //@RequestBody hace que los datos a guardar se almacenen en el cuerpo de la query
    private Task create(@RequestBody Task task){
        return this.taskRepository.save(task);
    }
    */

    //Opcion con mensaje de error si esto ocurre
    @PostMapping("/api/task")
    private ResponseEntity<?> create(@RequestBody Task task) { //@RequestBody hace que los datos a guardar se almacenen en el cuerpo de la query
        try {
            Task createdTask = this.taskRepository.save(task);
            return ResponseEntity.ok(createdTask); // Devuelve la tarea creada si la creación fue exitosa
        } catch (Exception e) {
            // Si ocurre algún error durante la creación, devuelve una respuesta de error con el mensaje de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la tarea: " + e.getMessage());
        }
    }

    /**
     * Modifica una tarea existente
     * @param task
     * @return Tarea modificada o mensaje de error
     */

    /*  Opcion sencilla
    @PutMapping("/api/task")
    private Task update(@RequestBody Task task){
        return this.taskRepository.save(task);
    }
    */

    //Opcion con mensaje de error si esto ocurre
    @PutMapping("/api/task")
    private ResponseEntity<?> update(@RequestBody Task task) { //@RequestBody hace que los datos a guardar se almacenen en el cuerpo de la query
        if (this.taskRepository.existsById(task.getId())) {
            try {
                Task updatedTask = this.taskRepository.save(task);
                return ResponseEntity.ok(updatedTask); // Devuelve la tarea modificada si la actualización fue exitosa
            } catch (Exception e) {
                // Si ocurre algún error durante la actualización, devuelve una respuesta de error con el mensaje de la excepción
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la tarea: " + e.getMessage());
            }
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tarea con id = " + task.getId() + " no existe!");
    }


    /* **** MÉTODO DELETE ANTERIOR
    /*
     * Elimina una tarea que coincide con la id dada
     * @param id Long
     * @return String
     */
    /*
    @DeleteMapping("/api/task/{id}")
    //@PathVariable define una variable como parámetro para ser usado en un método de repository
    private String delete(@PathVariable Long id){
        //Comprobamos si la tarea existe
        if (this.taskRepository.existsById(id)) {
            this.taskRepository.deleteById(id);
            return "Tarea " + id + " eliminada";
        } else return "La tarea " + id + " no existe. No se ha borrado ningún registro";
    }
     */

    // TODO revisar integridad referencial (Ojo con las asociaciones...CASCADE, etc)
    /**
     * Elimina una tarea que coincide con la id dada
     * @param id Long
     * @return ResponseEntity
     */
    @DeleteMapping("/api/task/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            try {
                taskRepository.deleteById(id);
                return ResponseEntity.ok("tarea " + id +" eliminada con éxito");
            } catch (Exception e) {
                // Si ocurre algún error durante la eliminación, devuelve una respuesta de error con el mensaje de la excepción
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la tarea: " + e.getMessage());
            }
        } else {
            // Si la etiqueta no existe, devuelve una respuesta de error con estado 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tarea con id = " + id + " no existe");
        }
    }
}
