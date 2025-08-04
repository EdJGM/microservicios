package publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloDTO {

    //Atributos del padre: publicacion
    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String isbn;
    private String resumen;
    private String idioma;
    //Atributos del hijo: articulo
    private String revista;
    private String doi;
    private String areaInvestigacion;
    private String fechaPublicacion;

    private long idAutor;
}
