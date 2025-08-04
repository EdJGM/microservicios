package ec.edu.espe.ms_notificaciones.controllers;

import ec.edu.espe.ms_notificaciones.dto.NotificacionDTO;
import ec.edu.espe.ms_notificaciones.entity.Notificacion;
import ec.edu.espe.ms_notificaciones.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    //inyeccion de dependencias
    @Autowired
    private NotificacionService notificacionService;

    // Endpoint para listar todas las notificaciones
    @GetMapping
    public List<Notificacion> listarNotificaciones(
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        return notificacionService.listarNotificaciones();
    }
}
