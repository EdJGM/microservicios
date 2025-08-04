package ec.edu.espe.ms_catalogo.services;

import ec.edu.espe.ms_catalogo.dto.CatalogoDTO;
import ec.edu.espe.ms_catalogo.entities.Catalogo;
import ec.edu.espe.ms_catalogo.repositories.CatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoService {

    @Autowired
    private CatalogoRepository catalogoRepository;

    @Autowired
    private NotificacionProducer notificacionProducer;

    public void guardarEnCatalogo(CatalogoDTO dto) {
        Catalogo catalogo = new Catalogo();

        // Mapear desde DTO a entidad
        catalogo.setTipo(dto.getTipo());
        catalogo.setTitulo(dto.getTitulo());
        catalogo.setIsbn(dto.getIsbn());
        catalogo.setAutor(dto.getAutor());
        catalogo.setFechaPublicacion(dto.getFechaPublicacion());
        catalogo.setEditorial(dto.getEditorial());
        catalogo.setResumen(dto.getResumen());
        catalogo.setIdioma(dto.getIdioma());

        // Campos específicos para libros
        catalogo.setGenero(dto.getGenero());
        catalogo.setNumeroPaginas(dto.getNumeroPaginas());
        catalogo.setEdicion(dto.getEdicion());

        // Campos específicos para artículos
        catalogo.setRevista(dto.getRevista());
        catalogo.setDoi(dto.getDoi());
        catalogo.setAreaInvestigacion(dto.getAreaInvestigacion());

        // Guardar en el catálogo
        Catalogo saved = catalogoRepository.save(catalogo);

        // Enviar notificación después de guardar en el catálogo
        notificacionProducer.enviarNotificacion(
                "Nueva publicación agregada al catálogo: " + saved.getTitulo(),
                "nueva_publicacion_catalogo"
        );
    }

    public List<Catalogo> listarCatalogo() {
        return catalogoRepository.findAll();
    }
}
