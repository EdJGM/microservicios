package publicaciones.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue relojQueue() {
        return QueueBuilder.durable("reloj.solicitud")
                .build();
    }

    @Bean
    public Queue relojAjusteQueue() {
        return QueueBuilder.durable("reloj.ajuste").build();
    }
}
