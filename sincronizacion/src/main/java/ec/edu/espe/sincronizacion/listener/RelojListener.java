package ec.edu.espe.sincronizacion.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.sincronizacion.dto.HoraClienteDTO;
import ec.edu.espe.sincronizacion.services.SincronizacionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelojListener {

    @Autowired
    private SincronizacionService sincronizacionService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "reloj.solicitud")
    public void recibirSolicitud(String mensajeJson) {
        try {
            HoraClienteDTO dto = objectMapper.readValue(mensajeJson, HoraClienteDTO.class);
            System.out.println("Recibido: " + dto);
            sincronizacionService.registrarTiempoCliente(dto);
        } catch (Exception e) {
            // Manejar excepciones y errores
            e.printStackTrace();
        }
    }
}
