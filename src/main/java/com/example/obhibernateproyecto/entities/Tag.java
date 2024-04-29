package com.example.obhibernateproyecto.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Representa la tabla ob_tag (etiquetas) en base de datos
 * Tag: representa una entidad etiqueta con los atributos id, nombre,
 *      color (enumTagColor, almacenada como string en base de datos)
 */
@Entity
@Table(name = "ob_tag")
public class Tag implements Serializable {
    /*
    TagColor: una enumeración, podrá ser de tipo BLUE, YELLOW, GREEN, RED.
    Tag: representa una entidad etiqueta con los atributos id, nombre,
         color (enumTagColor, almacenada como string en base de datos)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    EnumTagColor tagColor;

    // CONSTRUCTORES
    public Tag(){}

    public Tag(Long id, String name, EnumTagColor tagColor) {
        this.id = id;
        this.name = name;
        this.tagColor = tagColor;
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumTagColor getTagColor() {
        return tagColor;
    }

    public void setTagColor(EnumTagColor tagColor) {
        this.tagColor = tagColor;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tagColor=" + tagColor +
                '}';
    }
}
