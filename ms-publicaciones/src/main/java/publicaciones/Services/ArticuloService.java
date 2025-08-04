package publicaciones.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.Repository.ArticuloRepository;
import publicaciones.Repository.AutorRepository;
import publicaciones.dto.ArticuloDTO;
import publicaciones.dto.CatalogoDTO;
import publicaciones.dto.ResponseDTO;
import publicaciones.entity.Articulo;
import publicaciones.entity.Autor;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private NotificacionProducer producer;
    @Autowired
    private CatalogoProducer catalogoProducer;

    public ResponseDTO crearArticulo(ArticuloDTO dato){
        Articulo articulo = new Articulo();
        articulo.setTitulo(dato.getTitulo());
        articulo.setAnioPublicacion(dato.getAnioPublicacion());
        articulo.setEditorial(dato.getEditorial());
        articulo.setIsbn(dato.getIsbn());
        articulo.setResumen(dato.getResumen());
        articulo.setIdioma(dato.getIdioma());
        articulo.setRevista(dato.getRevista());
        articulo.setDoi(dato.getDoi());
        articulo.setAreaInvestigacion(dato.getAreaInvestigacion());
        articulo.setFechaPublicacion(dato.getFechaPublicacion());

        // Buscar y asignar el autor
        Autor autor = autorRepository.findById(dato.getIdAutor())
                .orElseThrow(() -> new RuntimeException("Autor con id " + dato.getIdAutor() + " no encontrado"));
        articulo.setAutor(autor);

        Articulo saved = articuloRepository.save(articulo); // Guarda el artículo en la base de datos

        //Notificar el evento
//        producer.enviarNotificacion(
//                "Artículo creado: " + saved.getTitulo(),
//                "nuevo_articulo" // Tipo de notificación
//        );

        // Enviar a la cola de catálogo
        CatalogoDTO catalogoDTO = new CatalogoDTO();
        catalogoDTO.setTipo("ARTICULO");
        catalogoDTO.setTitulo(saved.getTitulo());
        catalogoDTO.setIsbn(saved.getIsbn());
        catalogoDTO.setAutor(saved.getAutor().getNombre() + " " + saved.getAutor().getApellido());
        catalogoDTO.setFechaPublicacion(saved.getFechaPublicacion());
        catalogoDTO.setEditorial(saved.getEditorial());
        catalogoDTO.setResumen(saved.getResumen());
        catalogoDTO.setIdioma(saved.getIdioma());
        catalogoDTO.setRevista(saved.getRevista());
        catalogoDTO.setDoi(saved.getDoi());
        catalogoDTO.setAreaInvestigacion(saved.getAreaInvestigacion());

        catalogoProducer.enviarPublicacion(catalogoDTO);

        return new ResponseDTO(
                "Artículo registrado correctamente",
                saved// Guarda el artículo en la base de datos
        );
    }

    public ResponseDTO actualizarArticulo(Long id, ArticuloDTO dato){
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo con id " + id + " no encontrado"));

        articulo.setTitulo(dato.getTitulo());
        articulo.setAnioPublicacion(dato.getAnioPublicacion());
        articulo.setEditorial(dato.getEditorial());
        articulo.setIsbn(dato.getIsbn());
        articulo.setResumen(dato.getResumen());
        articulo.setIdioma(dato.getIdioma());
        articulo.setRevista(dato.getRevista());
        articulo.setDoi(dato.getDoi());
        articulo.setAreaInvestigacion(dato.getAreaInvestigacion());
        articulo.setFechaPublicacion(dato.getFechaPublicacion());

        // Buscar y asignar el autor
        Autor autor = autorRepository.findById(dato.getIdAutor())
                .orElseThrow(() -> new RuntimeException("Autor con id " + dato.getIdAutor() + " no encontrado"));
        articulo.setAutor(autor);

        return new ResponseDTO(
                "Artículo actualizado correctamente",
                articuloRepository.save(articulo) // Guarda el artículo en la base de datos
        );
    }

    public ResponseDTO listarArticulos(){
        return new ResponseDTO(
                "Lista de artículos",
                articuloRepository.findAll() // Obtiene todos los artículos de la base de datos
        );
    }

    public ResponseDTO buscarArticuloPorId(Long id){
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo con id " + id + " no encontrado"));

        return new ResponseDTO(
                "Artículo encontrado",
                articulo // Devuelve el artículo encontrado
        );
    }

    public ResponseDTO eliminarArticulo(Long id){
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo con id " + id + " no encontrado"));

        articuloRepository.delete(articulo);
        return new ResponseDTO("Artículo eliminado correctamente", null);
    }
}
