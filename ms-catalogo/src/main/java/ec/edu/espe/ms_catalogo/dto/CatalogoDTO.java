package ec.edu.espe.ms_catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoDTO {
    private String tipo;        // "LIBRO" o "ARTICULO"
    private String titulo;
    private String isbn;
    private String autor;       // Nombre completo del autor
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
