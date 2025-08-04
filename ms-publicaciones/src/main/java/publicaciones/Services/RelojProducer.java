package publicaciones.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.HoraClienteDTO;

import java.time.Instant;

@Service
public class RelojProducer {
    @Autowired
    private AmqpTemplate template;

    @Autowired
    private ObjectMapper mapper;

    private static final String nombreNodo = "ms-publicaciones";

    public void enviarHora(){
        try {
            HoraClienteDTO dto = new HoraClienteDTO(nombreNodo, Instant.now().toEpochMilli());
            String json = mapper.writeValueAsString(dto);
            template.convertAndSend("reloj.solicitud", json);
        } catch (Exception e) {
            // Manejar excepciones y errores
            e.printStackTrace();
        }
    }
}
