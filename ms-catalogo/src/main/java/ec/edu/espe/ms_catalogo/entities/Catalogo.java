package ec.edu.espe.ms_catalogo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; //libro o articulo
    private String titulo;
    private String isbn;
    private String autor;
    private String fechaPublicacion;
    private String editorial;
    private String resumen;
    private String idioma;

    // Campos específicos para libros
    private String genero;
    private Integer numeroPaginas;
    private String edicion;

    // Campos específicos para artículos
    private String revista;
    private String doi;
    private String areaInvestigacion;
}
