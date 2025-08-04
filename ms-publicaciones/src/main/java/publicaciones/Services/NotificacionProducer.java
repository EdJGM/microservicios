package publicaciones.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.NotificacionDTO;

@Service
public class NotificacionProducer {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    public void enviarNotificacion(String mensaje, String tipo){
        try {
            NotificacionDTO dto = new NotificacionDTO(mensaje, tipo);
            String json = objectMapper.writeValueAsString(dto);
            template.convertAndSend("notificaciones_cola", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
