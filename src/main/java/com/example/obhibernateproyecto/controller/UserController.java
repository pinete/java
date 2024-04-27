package com.example.obhibernateproyecto.controller;

import com.example.obhibernateproyecto.dao.UserDAO;
import com.example.obhibernateproyecto.entities.User;
import com.example.obhibernateproyecto.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO - crear servicio
 * Metodo actualizar
 * Pruebas de guardado cuando hay asociaciones
 * - la asociación billinginfo se guarda desde aqui
 * - la asociación task se guarda desde el controlador task
 */


// Controlador para los usuarios
@RestController
public class UserController {

    /*
     * Creamos estas dos variables privadas para inyectar en el controlador de usuarios
     * estos dos tipos de gestión de datos.
     * UserRepository proviene directamente de Spring.
     * UserDAO proviene de una clase que hemos creado.
     * Spring puede inyectar UserDAO porque UserDAOImpl está marcado como @Repository,
     * que indica a Spring que es una clase que gestiona datos.
     */
    private UserRepository userRepository;
    private UserDAO userDao;

    //Inyectamos userRepository y userDao
    public  UserController(UserRepository userRepository, UserDAO userDao){
        this.userRepository = userRepository;
        this.userDao = userDao;
    }

    /**
     * Devuelve una lista con todos los usuarios y sus asociaciones mediante el método GET
     * @return ListaUsuarios
     */
    @GetMapping("/api/users")
    private List<User> findAll(){
        return this.userRepository.findAll();
    }

    /**
     * Devuelve una lista de todos los usuarios activos mediante el método GET
     * @return ListaUsuariosActivos
     */
    @GetMapping("/api/users/active")
    private List<User> findAllActive(){
        return this.userDao.findAllActive();
    }

    //NOTA: Spring reconoce el nombre del campo y la palabra BETWEEN en el nombre del método y automáticamente realiza la query con between.
    //      Cuando llamas al método findByBirthdateBetween(startDeliveryDate, endDeliveryDate) en el repositorio, Spring Data JPA traduce
    //      esto a una consulta SQL similar a:
    //          SELECT * FROM Task WHERE deliveryDate BETWEEN startDeliveryDate AND endDeliveryDate;
    /**
     * Lista los usuarios cuya fecha de nacimiento está comprendida entre las fechas dadas
     * @param startBirthdate
     * @param endBirthdate
     * @return Lista de usuarios entre fechas de nacimiento
     */
    @GetMapping("/api/users/betweendates")
    private List<User> findAllBetweenBirthDate(@RequestParam("startBirthDate") String startBirthdate,
                                                @RequestParam("endBirthDate") String endBirthdate){
        return this.userRepository.findByBirthDateBetween(LocalDate.parse(startBirthdate),LocalDate.parse(endBirthdate));
    }

    /**
     * Crea un nuevo usuario mediante método POST
     * @param user
     * @return Usuario a guardar
     */
    @PostMapping("/api/users")
    //@RequestBody hace que los datos a guardar se almacenen en el cuerpo de la query
    private User create(@RequestBody User user){
    return this.userRepository.save(user);
    }

    /**
     * Modifica un usuario existente
     * @param user
     * @return Usuario modificado
     */
    @PutMapping("/api/users")
    //@RequestBody hace que los datos a guardar se almacenen en el cuerpo de la query
    private User update(@RequestBody User user){
        return this.userRepository.save(user);
    }

    /**
     * Elimina un usuario que coincide con la id dada
     * @param id
     * @return Response string
     */
    @DeleteMapping("/api/users")
    //@PathVariable define una variable como parámetro para ser usado en un método de repository
    // Ej. http://localhost:8080/api/usuarios/10
    //@RequestParam solicita en la url una variable como parámetro, definida con un nombre determinado.
    // Ej. http://localhost:8080/api/usuarios?id=10
    private ResponseEntity<?>  delete(@RequestParam("id") Long id){
        //Comprobamos si el usuario existe
        if (this.userRepository.existsById(id)) {
            try {
                this.userRepository.deleteById(id); // TODO revisar integridad referencial (Ojo con las asociaciones...CASCADE, etc)
                return ResponseEntity.ok("Usuario " + id + " eliminado con éxito") ;
            } catch (Exception e) {
                // Si ocurre algún error durante la eliminación, devuelve una respuesta de error con el mensaje de la excepción
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario: " + e.getMessage());
            }

        } else {
            // Si la etiqueta no existe, devuelve una respuesta de error con estado 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario " + id + " no existe");
        }
    }
}
