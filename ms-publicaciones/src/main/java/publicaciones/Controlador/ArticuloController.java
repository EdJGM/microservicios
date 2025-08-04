package publicaciones.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import publicaciones.Services.ArticuloService;
import publicaciones.dto.ArticuloDTO;
import publicaciones.dto.ResponseDTO;

@RestController
@RequestMapping("/publicaciones/articulo")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @GetMapping
    public ResponseDTO listarArticulos(
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        return articuloService.listarArticulos(); // Llama al servicio para obtener la lista de artículos
    }

    @PostMapping
    public ResponseDTO crearArticulo(
            @RequestBody ArticuloDTO dato,
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {

        // Solo ADMIN puede eliminar autores
        if (!roles.contains("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDTO("Sin permisos para eliminar autores", null)).getBody();
        }
        return articuloService.crearArticulo(dato); // Llama al servicio para crear un nuevo artículo
    }

    @PutMapping("/{id}")
    public ResponseDTO actualizarArticulo(
            @PathVariable Long id, @RequestBody ArticuloDTO dato,
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        return articuloService.actualizarArticulo(id, dato); // Llama al servicio para actualizar un artículo existente
    }

    @GetMapping("/{id}")
    public ResponseDTO buscarArticuloPorId(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        return articuloService.buscarArticuloPorId(id); // Llama al servicio para buscar un artículo por su ID
    }

    @DeleteMapping("/{id}")
    public ResponseDTO eliminarArticulo(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        return articuloService.eliminarArticulo(id); // Llama al servicio para eliminar un artículo por su ID
    }
}
