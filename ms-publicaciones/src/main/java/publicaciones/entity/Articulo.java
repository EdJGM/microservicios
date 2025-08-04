package publicaciones.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "papers")
@Getter
@Setter
public class Articulo extends Publicacion{

    private String revista;
    @Column(name = "doi", unique = true)
    private String doi;
    private String areaInvestigacion;
    private String fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;
}
