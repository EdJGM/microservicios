package ec.edu.espe.ms_catalogo.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelojListener {

    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "reloj.ajuste")
    public void recibirAjuste(String mensajeJson) {
        try {
            // Suponiendo que el mensaje es solo la hora sincronizada (long)
            Long horaSincronizada = Long.valueOf(mensajeJson);

            // Obtener la hora local actual
            long horaLocal = System.currentTimeMillis();

            long diferencia = horaSincronizada - horaLocal;

            System.out.println("Hora sincronizada recibida del servidor: " + horaSincronizada);

            if (diferencia > 0) {
                System.out.println("La hora local se AUMENTA en " + diferencia + " ms para sincronizar con el servidor.");
            } else if (diferencia < 0) {
                System.out.println("La hora local se REDUCE en " + Math.abs(diferencia) + " ms para sincronizar con el servidor.");
            } else {
                System.out.println("La hora local ya está sincronizada con el servidor.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}