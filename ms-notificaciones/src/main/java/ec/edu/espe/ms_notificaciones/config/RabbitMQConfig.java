package ec.edu.espe.ms_notificaciones.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue catalogoQueue() {
        return QueueBuilder.durable("catalogo.cola")
                .build();
    }

    @Bean
    public Queue notificacionesQueue() {
        return QueueBuilder.durable("notificaciones_cola")
                .build();
    }

    @Bean
    public Queue relojQueue() {
        return QueueBuilder.durable("reloj.solicitud").build();
    }

    @Bean
    public Queue relojAjusteQueue() {
        return QueueBuilder.durable("reloj.ajuste").build();
    }
}
