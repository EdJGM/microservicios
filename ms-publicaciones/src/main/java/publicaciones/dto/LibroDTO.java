package publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTO {

    //Atributos del padre: publicacion
    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String isbn;
    private String resumen;
    private String idioma;
    //Atributos del hijo: libro
    private String genero;
    private int numeroPaginas;
    private String edicion;

    private long idAutor;

}
