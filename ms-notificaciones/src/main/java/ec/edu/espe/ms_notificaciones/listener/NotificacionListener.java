package ec.edu.espe.ms_notificaciones.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.ms_notificaciones.dto.NotificacionDTO;
import ec.edu.espe.ms_notificaciones.services.NotificacionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificacionListener {

    @Autowired
    private NotificacionService notificacionService;

    //coge un json y transforma a dto
    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "notificaciones_cola")
    public void recibirNotificacion(String mensajeJson){
        try {
            // Convertir el mensaje JSON a NotificacionDTO
            NotificacionDTO dto = mapper.readValue(mensajeJson, NotificacionDTO.class);
            // Guardar la notificación usando el servicio
            notificacionService.guardarNotificacion(dto);
        } catch (Exception e) {
            //imprimir error
            e.printStackTrace();
        }
    }

}
