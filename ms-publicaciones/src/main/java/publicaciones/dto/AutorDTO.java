package publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String orcid;
    private String institucion;
    private String nacionalidad;
    private String telefono;
    private String fechaNacimiento;
}