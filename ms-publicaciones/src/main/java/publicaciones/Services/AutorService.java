package publicaciones.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.Repository.AutorRepository;
import publicaciones.dto.AutorDTO;
import publicaciones.dto.NotificacionDTO;
import publicaciones.dto.ResponseDTO;
import publicaciones.entity.Autor;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    // Autowired: crea una instancia de la clase AutorRepository
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer producer;


    public ResponseDTO crearAutor(AutorDTO dato){
        Autor autor = new Autor();
        autor.setNombre(dato.getNombre());
        autor.setApellido(dato.getApellido());
        autor.setEmail(dato.getEmail());
        autor.setOrcid(dato.getOrcid());
        autor.setInstitucion(dato.getInstitucion());
        autor.setNacionalidad(dato.getNacionalidad());
        autor.setTelefono(dato.getTelefono());
        autor.setFechaNacimiento(dato.getFechaNacimiento());

        Autor saved = autorRepository.save(autor); // Guarda el autor en la base de datos

        //notificar el evento
        producer.enviarNotificacion(
                "Autor creado: " + saved.getNombre() + " " + saved.getApellido(),
                "nuevo_autor"
        );

        return new ResponseDTO(
                "Autor registradp correctamente",
                saved// Guarda el autor en la base de datos
        );
    }

    public ResponseDTO actualizarAutor(Long id, AutorDTO dato){
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor con id " + id + " no encontrado"));

        autor.setNombre(dato.getNombre());
        autor.setApellido(dato.getApellido());
        autor.setEmail(dato.getEmail());
        autor.setOrcid(dato.getOrcid());
        autor.setInstitucion(dato.getInstitucion());
        autor.setNacionalidad(dato.getNacionalidad());
        autor.setTelefono(dato.getTelefono());
        autor.setFechaNacimiento(dato.getFechaNacimiento());

        return new ResponseDTO(
                "Autor actualizado correctamente",
                autorRepository.save(autor) // Guarda el autor en la base de datos
        );
    }

    public List<ResponseDTO> listarAutores(){
        return autorRepository.findAll().stream()
                .map(a -> new ResponseDTO("Autor: " + a.getNombre(), a))
                .collect(Collectors.toList());
    }

    public ResponseDTO buscarAutorPorId(Long id){
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor con id " + id + " no encontrado"));

        return new ResponseDTO("Autor encontrado", autor);
    }

    public ResponseDTO eliminarAutor(Long id){
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor con id " + id + " no encontrado"));

        autorRepository.delete(autor);
        return new ResponseDTO("Autor eliminado correctamente", null);
    }

}
