package ec.edu.espe.ms_catalogo.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.ms_catalogo.dto.CatalogoDTO;
import ec.edu.espe.ms_catalogo.services.CatalogoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogoListener {

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "catalogo.cola")
    public void recibirPublicacion(String mensajeJson) {
        try {
            // Convertir el mensaje JSON a CatalogoDTO
            CatalogoDTO dto = mapper.readValue(mensajeJson, CatalogoDTO.class);

            // Guardar en el catálogo
            catalogoService.guardarEnCatalogo(dto);

            System.out.println("Nueva publicación guardada en catálogo: " + dto.getTitulo());
        } catch (Exception e) {
            System.err.println("Error al procesar mensaje de catálogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
