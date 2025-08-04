package ec.edu.espe.ms_catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoraClienteDTO {
    private String nombreNodo;
    private String horaEnviada;
}