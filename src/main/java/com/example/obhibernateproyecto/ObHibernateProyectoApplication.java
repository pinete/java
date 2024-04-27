package com.example.obhibernateproyecto;

import com.example.obhibernateproyecto.entities.BillingInfo;
import com.example.obhibernateproyecto.entities.Task;
import com.example.obhibernateproyecto.entities.User;
import com.example.obhibernateproyecto.repository.BillingInfoRepository;
import com.example.obhibernateproyecto.repository.TagRepository;
import com.example.obhibernateproyecto.repository.TaskRepository;
import com.example.obhibernateproyecto.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class ObHibernateProyectoApplication {

	public static void main(String[] args) {
        ApplicationContext context =SpringApplication.run(ObHibernateProyectoApplication.class, args);

        // Billing Info y User ========================================================================
        // VAMOS A PROBAR SI FUNCIONA RELLENANDO DATOS INICIALES...
        // (También podíamos comprobar su funcionamiento creando un test)

        //Creamos los repositorios
        BillingInfoRepository billingInfoRepository = context.getBean(BillingInfoRepository.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        TagRepository tagRepository = context.getBean(TagRepository.class);
        TaskRepository taskRepository = context.getBean(TaskRepository.class);

        //Creamos y guardamos la factura
        BillingInfo info1 = new BillingInfo(null,"ES12345678945642","España","Almeria","04001","Callecita",null);
        billingInfoRepository.save(info1);

        //Creamos los usuarios
        User user1 = new User(null, LocalDate.now(),true,"27474547R","Perez","Antonio");
        User user2 = new User(null, LocalDate.of(1970,5,17),false,"25234547P","Gomez","Felipe");
        //Insertamos en el usuario la factura que antes creamos. Lo hacemos del lado del usuario, ya que es el propietario de la factura
        user1.setBillingInfo(info1);
        // Guardamos el usuario
        userRepository.save(user1);
        userRepository.save(user2);

        // Creamos las tareas ==================================================================================
        Task task1 = new Task(null,"Tarea 1","Esta es la tarea 1 lorem ipsum ...",false, LocalDate.of(2022,1,1), user1);
        Task task2 = new Task(null,"Tarea 2","Esta es la tarea 2 mentem cato ...",true, LocalDate.of(2022,2,1), user1);
        Task task3 = new Task(null,"Tarea 3","Esta es la tarea 3 corpus cristi ...",false, LocalDate.of(2022,3,1), user2);
        Task task4 = new Task(null,"Tarea 4","Esta es la tarea 4 natum cabronatum ...",true, LocalDate.of(2022, 5,1), user2);
        // Como ejemplo guardamos las tareas en una sola sentencia almacenándolas en una lista
        taskRepository.saveAll(List.of(task1, task2, task3, task4));

	}
}
