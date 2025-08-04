package publicaciones.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.CatalogoDTO;

@Service
public class CatalogoProducer {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    public void enviarPublicacion(CatalogoDTO dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            template.convertAndSend("catalogo.cola", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
