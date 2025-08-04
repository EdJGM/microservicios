package ec.edu.espe.sincronizacion.services;

import ec.edu.espe.sincronizacion.dto.HoraClienteDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SincronizacionService {

    @Autowired
    private AmqpTemplate template;

    private final Map<String, Long> tiempoClientes = new ConcurrentHashMap<>();

    private static int INTERVALO_SEGUNDOS = 10;

    public void registrarTiempoCliente(HoraClienteDTO dto){
        tiempoClientes.put(dto.getNombreNodo(), Long.valueOf(dto.getHoraEnviada()));
    }

    public void sincronizarRelojes(){
        if(tiempoClientes.size() >= 2) {
            long shora = Instant.now().toEpochMilli();
            long promedio = (shora + tiempoClientes.values().stream().mapToLong(Long::longValue).sum()) / (tiempoClientes.size() + 1);
            tiempoClientes.clear();// limpiar para evitar duplicados
            enviarAjusteReloj(promedio);
        }
    }

    public void enviarAjusteReloj(Long horaServidor){
        template.convertAndSend("reloj.ajuste", String.valueOf(horaServidor));
        //fomato de horaServidor: 2023-10-01T12:00:00Z
        System.out.println("Ajustando reloj de los clientes a: " + Instant.ofEpochMilli(horaServidor));
    }
}
