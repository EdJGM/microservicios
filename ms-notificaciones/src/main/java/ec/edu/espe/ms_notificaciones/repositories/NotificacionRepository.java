package ec.edu.espe.ms_notificaciones.repositories;

import ec.edu.espe.ms_notificaciones.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}
