package publicaciones.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "autores")
@Getter
@Setter
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, name = "nombre")
    private String nombre;

    @Column(nullable = false, length = 50, name = "apellido")
    private String apellido;

    @Column(nullable = false, length = 20, name = "email", unique = true)
    private String email;

    @Column(nullable = false, length = 20, name = "orcid", unique = true)
    private String orcid;

    @Column(nullable = false, length = 50, name = "institucion")
    private String institucion;

    @Column(nullable = false, length = 50, name = "nacionalidad")
    private String nacionalidad;

    @Column(nullable = false, length = 20, name = "telefono")
    private String telefono;

    @Column(nullable = false, length = 50, name = "fechaNacimiento")
    private String fechaNacimiento;

    @OneToMany(mappedBy = "autor")
    @JsonIgnore
    private List<Libro> libros;

    @OneToMany(mappedBy = "autor")
    @JsonIgnore
    private List<Articulo> articulos;
}
