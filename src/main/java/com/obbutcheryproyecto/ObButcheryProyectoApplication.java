package com.obbutcheryproyecto;

import com.obbutcheryproyecto.entities.*;
import com.obbutcheryproyecto.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ObButcheryProyectoApplication {

	public static void main(String[] args) {
        ApplicationContext context =SpringApplication.run(ObButcheryProyectoApplication.class, args);

        // Billing Info y User ========================================================================
        // VAMOS A PROBAR SI FUNCIONA RELLENANDO DATOS INICIALES...
        // (También podíamos comprobar su funcionamiento creando un test)

        //Creamos los repositorios
        BillingInfoRepository billingInfoRepository = context.getBean(BillingInfoRepository.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        TagRepository tagRepository = context.getBean(TagRepository.class);
        TaskRepository taskRepository = context.getBean(TaskRepository.class);
        VatRepository vatRepository = context.getBean(VatRepository.class);
        ArticleRepository articleRepository = context.getBean(ArticleRepository.class);

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
        Task task1 = new Task(null,"Tarea 1","Esta es la tarea 1 lorem ipsum ...",null, LocalDate.of(2022,1,1), user1);
        Task task2 = new Task(null,"Tarea 2","Esta es la tarea 2 mentem cato ...",true, LocalDate.of(2022,2,1), user1);
        Task task3 = new Task(null,"Tarea 3","Esta es la tarea 3 corpus cristi ...",false, LocalDate.of(2022,3,1), user2);
        Task task4 = new Task(null,"Tarea 4","Esta es la tarea 4 natum cabronatum ...",true, LocalDate.of(2022, 5,1), user2);
        // Guardamos las tareas usando una sola sentencia almacenándolas en una lista
        taskRepository.saveAll(List.of(task1, task2, task3, task4));

        // Creamos las tasas (IVA)
        Vat iva21 = new Vat();
        iva21.setRatePercent(21);
        iva21.setSurchargePercent(BigDecimal.valueOf(5.2));
        iva21.setTaxName("IVA21");
        iva21.setCodCountry(34);
        iva21.setApplicationDate(LocalDate.of(2024,1,1));

        Vat iva10 = new Vat();
        iva10.setRatePercent(10);
        iva10.setSurchargePercent(BigDecimal.valueOf(1.4));
        iva10.setTaxName("IVA10");
        iva10.setCodCountry(34);
        iva10.setApplicationDate(LocalDate.of(2024,1,1));

        Vat iva4 = new Vat();
        iva4.setRatePercent(4);
        iva4.setSurchargePercent(BigDecimal.valueOf(0.5));
        iva4.setTaxName("IVA4");
        iva4.setCodCountry(34);
        iva4.setApplicationDate(LocalDate.of(2024,1,1));

        vatRepository.saveAll(List.of(iva4,iva10,iva21));

        // Creamos Articulos
        Article article1 = new Article();
        article1.setDescription("Articulo 1");
        article1.setImage(null);
        article1.setIvaIncl(true);
        article1.setCodArt("ART0001");
        article1.setPrice(BigDecimal.valueOf(16.24));
        /* Omitimos esto porque ya se crea automáticamente con @PrePersist
        article1.setCreatedDate(LocalDateTime.of(2024,1,22,0,0,0));
        article1.setUpdatedDate(LocalDateTime.of(2024,1,28,0,0,0));
        */
        article1.setTaxId(iva21);

        Article article2 = new Article();
        article2.setDescription("Articulo 2");
        article2.setImage(null);
        article2.setIvaIncl(true);
        article2.setCodArt("ART0002");
        article2.setPrice(BigDecimal.valueOf(21.24));
        /* Omitimos esto porque ya se crea automáticamente con @PrePersist
        article2.setCreatedDate(LocalDateTime.of(2024,2,9,0,0,0));
        article2.setUpdatedDate(LocalDateTime.of(2024,3,14,0,0,0));
         */
        article2.setTaxId(iva10);

        Article article3 = new Article();
        article3.setDescription("Articulo 3");
        article3.setImage(null);
        article3.setIvaIncl(true);
        article3.setCodArt("ART0003");
        article3.setPrice(BigDecimal.valueOf(6.85));
        /* Omitimos esto porque ya se crea automáticamente con @PrePersist
        article3.setCreatedDate(LocalDateTime.of(2024,1,14,0,0,0));
        article3.setUpdatedDate(LocalDateTime.of(2024,3,26,0,0,0));
         */
        article3.setTaxId(iva21);

        articleRepository.saveAll(List.of(article1, article2, article3));

	}
}
