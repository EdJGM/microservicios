package ec.edu.espe.ms_catalogo.config;

import ec.edu.espe.ms_catalogo.services.RelojProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Autowired
    private RelojProducer relojProducer;

    @Scheduled(fixedRate = 10000)
    public void reportarHora(){
        try {
            relojProducer.enviarHora();
            System.out.println("Nodo: ms-catalogo -> Enviando hora");
        } catch (Exception e) {
            System.out.println("Error al reportar la hora: " + e.getMessage());
        }
    }
}
