package publicaciones.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import publicaciones.Services.LibroService;
import publicaciones.dto.LibroDTO;
import publicaciones.dto.ResponseDTO;

@RestController
@RequestMapping("/publicaciones/libro")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public ResponseDTO listarLibros(
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        return libroService.listarLibros(); // Llama al servicio para obtener la lista de libros
    }

    @PostMapping
    public ResponseDTO crearLibro(
            @RequestBody LibroDTO dato,
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        // Solo ADMIN puede eliminar autores
        if (!roles.contains("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDTO("Sin permisos para eliminar autores", null)).getBody();
        }
        return libroService.crearLibro(dato); // Llama al servicio para crear un nuevo libro
    }

    @PutMapping("/{id}")
    public ResponseDTO actualizarLibro(
            @PathVariable Long id, @RequestBody LibroDTO dato,
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        return libroService.actualizarLibro(id, dato); // Llama al servicio para actualizar un libro existente
    }

    @GetMapping("/{id}")
    public ResponseDTO buscarLibroPorId(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        return libroService.buscarLibroPorId(id); // Llama al servicio para buscar un libro por su ID
    }

    @DeleteMapping("/{id}")
    public ResponseDTO eliminarLibro(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        return libroService.eliminarLibro(id); // Llama al servicio para eliminar un libro por su ID
    }
}
