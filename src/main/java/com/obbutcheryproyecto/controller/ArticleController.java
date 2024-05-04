package com.obbutcheryproyecto.controller;

import com.obbutcheryproyecto.entities.Article;
import com.obbutcheryproyecto.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {
    @Autowired
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Devuelve una lista con todos los artículos mediante método GET ("/articles")
     * @return Lista de artículos
     */
    @GetMapping("/articles")
    private List<Article> findAll() {return this.articleRepository.findAll();}

    /**
     * Devuelve la imagen del artículo que coincide con la id dada o el error si se produce
     * Formato url mediante método GET ("/article/image/{id}")
     * @param id @Id
     * @return image
     */
    @GetMapping("/article/image/{id}")
    private byte[] findImage(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return article.getImage();
    }

    /**
     * Guarda en DB el artículo definido en el Body en fto JSON mediante método POST.("article/save")
     * No se deben envíar los campos createdDate y updatedDate, ya que estos se generan automáticamente.
     * @param article Article
     * @param image MultipartFile
     * @return String message
     * @throws IOException
     */
    @PostMapping("article/save")
    private String saveArticle(@RequestBody Article article, @RequestBody MultipartFile image) throws IOException {
        /*
        Algunos de los métodos más comunes que proporciona la
        interfaz MultipartFile son:
        String getName(): Devuelve el nombre original del archivo en la máquina cliente.
        String getOriginalFilename(): Devuelve el nombre original del archivo tal como
                                      fue proporcionado por el cliente en su máquina.
        String getContentType(): Devuelve el tipo MIME del archivo.
        long getSize(): Devuelve el tamaño del archivo en bytes.
        byte[] getBytes(): Devuelve los bytes del archivo como un array de bytes.
        InputStream getInputStream(): Devuelve un InputStream que permite leer los
                                      datos del archivo.
        En resumen, MultipartFile es una interfaz útil para trabajar con archivos
        subidos en aplicaciones web Spring MVC, ya que encapsula toda la
        información necesaria sobre el archivo y proporciona métodos convenientes
        para acceder a sus datos.
        */

        //NOTA: createdDate y UpdatedDate se crean automáticamente desde la clase mediante @PrePersist
        //      si no se incluyen en el Json del body
        if (image != null && !image.isEmpty())  article.setImage(image.getBytes());
        articleRepository.save(article);
        return "Artículo guardado con id = " + article.getId();
    }

    /**
     * Modifica en DB el artículo definido en el Body en fto JSON mediante método PUT.("/article/update")
     * No se deben envíar los campos createdDate y updatedDate, ya que estos se generan automáticamente.
     * @param article Article
     * @param image image MultipartFile
     * @return string message
     * @throws IOException
     */
    @PutMapping("/article/update")
    private String updateArticle(@RequestBody Article article, MultipartFile image) throws IOException {

        if (this.articleRepository.existsById(article.getId())) {
            // Si no enviamos en el Json el campo createdDate mantenemos la anterior fecha de creación...
            // La fecha de actualización (updatedDate) se genera solo en la clase gracias a @PreUpdate
            if(article.getCreatedDate() == null) {
                Article existingArticle = articleRepository.getById(article.getId());
                article.setCreatedDate(existingArticle.getCreatedDate());
            }

            articleRepository.save(article);
            return "Artículo con id = " + article.getId() + " modificado correctamente";
        } else return "La tarea con id " + article.getId() + "no existe";
    }





}
