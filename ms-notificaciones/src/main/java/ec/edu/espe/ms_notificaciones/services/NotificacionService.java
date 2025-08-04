package ec.edu.espe.ms_notificaciones.services;

import ec.edu.espe.ms_notificaciones.dto.NotificacionDTO;
import ec.edu.espe.ms_notificaciones.entity.Notificacion;
import ec.edu.espe.ms_notificaciones.repositories.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    //guardar notificacion
    public void guardarNotificacion(NotificacionDTO notificacion) {
        Notificacion nuevaNotificacion = new Notificacion();
        nuevaNotificacion.setMensaje(notificacion.getMensaje());
        nuevaNotificacion.setTipo(notificacion.getTipo());
        nuevaNotificacion.setFecha(LocalDateTime.now());

        notificacionRepository.save(nuevaNotificacion);
    }

    //listar todas las notificaciones
    public List<Notificacion> listarNotificaciones() {
        return notificacionRepository.findAll();
    }
}
