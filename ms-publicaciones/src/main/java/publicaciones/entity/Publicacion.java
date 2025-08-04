package publicaciones.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// una clase abstracta es una clase que no se puede instanciar, es decir, no se puede crear un objeto de esa clase.

@Entity
@Getter
@Setter

// investigar como funciona el @Inheritance herencia a nivel de base de datos
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String isbn;
    private String resumen;
    private String idioma;
}
