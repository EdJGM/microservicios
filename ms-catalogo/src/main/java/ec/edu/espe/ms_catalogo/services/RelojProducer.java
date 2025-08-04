package ec.edu.espe.ms_catalogo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.edu.espe.ms_catalogo.dto.HoraClienteDTO;

import java.time.Instant;

@Service
public class RelojProducer {
    @Autowired
    private AmqpTemplate template;

    @Autowired
    private ObjectMapper mapper;

    private static final String nombreNodo = "ms-catalogo";

    public void enviarHora(){
        try {
            HoraClienteDTO dto = new HoraClienteDTO(nombreNodo, String.valueOf(Instant.now().toEpochMilli()));
            String json = mapper.writeValueAsString(dto);
            template.convertAndSend("reloj.solicitud", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}