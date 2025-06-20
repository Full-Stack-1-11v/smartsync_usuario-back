package cl.ecomarket.user.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.ecomarket.user.assembles.UserProdExtrerAssembler;
import cl.ecomarket.user.client.ProductoService;
import cl.ecomarket.user.dto.ProductoDto;

import cl.ecomarket.user.dto.UserProductoDto;
import cl.ecomarket.user.model.User;
import cl.ecomarket.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
/**
 * Controlador REST para gestionar productos externos.
 * Proporciona endpoints para listar productos externos y obtener productos asociados a usuarios.
 */
@RestController
@RequestMapping("/api/v1/ecomarket/producto")
@Tag(name = "Producto Externo", description = "Controlador para gestionar productos externos")
public class ProductoExternoController {

        private static final Logger logger = LoggerFactory.getLogger(ProductoExternoController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProdExtrerAssembler userProdExtrerAssembler;
/**
     * Lista todos los productos externos disponibles.
     * @return Lista de productos externos en formato HATEOAS, o una respuesta vacía si no hay productos disponibles.
     */
    @GetMapping()
    @Operation(summary = "Listar productos externos", description = "Obtiene una lista de todos los productos externos disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos externos obtenida correctamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDto.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron productos externos."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<CollectionModel<EntityModel<ProductoDto>>> listarProductos() {
        logger.info("Listando todos los productos externos");
         // Llama al servicio para obtener la lista de productos
        List<ProductoDto> productos = productoService.listarProductos();
        if (productos == null || productos.isEmpty()) {
            logger.warn("No se encontraron productos externos");
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<ProductoDto>> productoModels = productos.stream()
                .map(userProdExtrerAssembler::toModelProducto)
                .toList();
        logger.info("Productos externos encontrados: {}", productoModels.size());
        return ResponseEntity.ok(CollectionModel.of(productoModels));
    }

    /**
     * Obtiene un producto externo asociado a un usuario por su ID.
     * @param id ID del usuario/producto.
     * @return Producto externo asociado al usuario en formato HATEOAS, o una respuesta 404 si no se encuentra el producto o el usuario.
     */
    @GetMapping("/userproductodto/{id}")
    @Operation(summary = "Obtener producto por ID de usuario", description = "Obtiene un producto externo asociado a un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto externo asociado al usuario encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProductoDto.class))),
            @ApiResponse(responseCode = "404", description = "Producto o usuario no encontrado."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<EntityModel<UserProductoDto>> obtenerProductoPorIduser(@PathVariable("id") Long id) {
        ProductoDto producto = productoService.obtenerProductoPorId(id);
        logger.info("Buscando producto externo con ID: {}", id);
        if (producto == null) {
            logger.warn("Producto externo con ID {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        User user;
        try {
            user = userService.userId(id.intValue());
        } catch (Exception e) {
            logger.error("Error al buscar usuario con ID = {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
        UserProductoDto userProductoDto = new UserProductoDto();
        userProductoDto.setProducto(producto);
        userProductoDto.setUser(user);

        // Aplica el assembler para HATEOAS
        EntityModel<UserProductoDto> model = userProdExtrerAssembler.toModelUserProd(userProductoDto);
        logger.info("Producto externo asociado al usuario encontrado: {}", userProductoDto);
        return ResponseEntity.ok(model);
    }
}
