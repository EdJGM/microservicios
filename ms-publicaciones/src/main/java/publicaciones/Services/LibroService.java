package publicaciones.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.Repository.AutorRepository;
import publicaciones.Repository.LibroRepository;
import publicaciones.dto.CatalogoDTO;
import publicaciones.dto.LibroDTO;
import publicaciones.dto.ResponseDTO;
import publicaciones.entity.Autor;
import publicaciones.entity.Libro;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private NotificacionProducer producer;
    @Autowired
    private CatalogoProducer catalogoProducer;

    public ResponseDTO crearLibro(LibroDTO dato) {
        Libro libro = new Libro();
        libro.setTitulo(dato.getTitulo());
        libro.setAnioPublicacion(dato.getAnioPublicacion());
        libro.setEditorial(dato.getEditorial());
        libro.setIsbn(dato.getIsbn());
        libro.setResumen(dato.getResumen());
        libro.setIdioma(dato.getIdioma());
        libro.setGenero(dato.getGenero());
        libro.setNumeroPaginas(dato.getNumeroPaginas());
        libro.setEdicion(dato.getEdicion());

        // Buscar y asignar el autor
        Autor autor = autorRepository.findById(dato.getIdAutor())
                .orElseThrow(() -> new RuntimeException("Autor con id " + dato.getIdAutor() + " no encontrado"));
        libro.setAutor(autor);

        Libro saved = libroRepository.save(libro);

        // Notificar el evento
//        producer.enviarNotificacion(
//                "Libro creado: " + saved.getTitulo(),
//                "nuevo_libro" // Tipo de notificación
//        );

        // Enviar a la cola de catálogo
        CatalogoDTO catalogoDTO = new CatalogoDTO();
        catalogoDTO.setTipo("LIBRO");
        catalogoDTO.setTitulo(saved.getTitulo());
        catalogoDTO.setIsbn(saved.getIsbn());
        catalogoDTO.setAutor(saved.getAutor().getNombre() + " " + saved.getAutor().getApellido());
        catalogoDTO.setFechaPublicacion(String.valueOf(saved.getAnioPublicacion()));
        catalogoDTO.setEditorial(saved.getEditorial());
        catalogoDTO.setResumen(saved.getResumen());
        catalogoDTO.setIdioma(saved.getIdioma());
        catalogoDTO.setGenero(saved.getGenero());
        catalogoDTO.setNumeroPaginas(saved.getNumeroPaginas());
        catalogoDTO.setEdicion(saved.getEdicion());

        catalogoProducer.enviarPublicacion(catalogoDTO);

        return new ResponseDTO(
                "Libro registrado correctamente",
                saved
        );
    }

    public ResponseDTO actualizarLibro(Long id, LibroDTO dato) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro con id " + id + " no encontrado"));

        libro.setTitulo(dato.getTitulo());
        libro.setAnioPublicacion(dato.getAnioPublicacion());
        libro.setEditorial(dato.getEditorial());
        libro.setIsbn(dato.getIsbn());
        libro.setResumen(dato.getResumen());
        libro.setIdioma(dato.getIdioma());
        libro.setGenero(dato.getGenero());
        libro.setNumeroPaginas(dato.getNumeroPaginas());
        libro.setEdicion(dato.getEdicion());

        // Buscar y asignar el autor
        Autor autor = autorRepository.findById(dato.getIdAutor())
                .orElseThrow(() -> new RuntimeException("Autor con id " + dato.getIdAutor() + " no encontrado"));
        libro.setAutor(autor);

        return new ResponseDTO(
                "Libro actualizado correctamente",
                libroRepository.save(libro) // Guarda el libro en la base de datos
        );
    }

    public ResponseDTO listarLibros() {
        return new ResponseDTO(
                "Lista de libros",
                libroRepository.findAll() // Obtiene todos los libros de la base de datos
        );
    }

    public ResponseDTO buscarLibroPorId(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro con id " + id + " no encontrado"));

        return new ResponseDTO(
                "Libro encontrado",
                libro // Devuelve el libro encontrado
        );
    }

    public ResponseDTO eliminarLibro(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro con id " + id + " no encontrado"));

        libroRepository.delete(libro); // Elimina el libro de la base de datos

        return new ResponseDTO(
                "Libro eliminado correctamente",
                null // No se devuelve ningún objeto, solo un mensaje
        );
    }
}
