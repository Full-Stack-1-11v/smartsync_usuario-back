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

import cl.ecomarket.user.assembles.UserProdExtrerAssembler;
import cl.ecomarket.user.client.ProductoService;
import cl.ecomarket.user.dto.ProductoDto;

import cl.ecomarket.user.dto.UserProductoDto;
import cl.ecomarket.user.model.User;
import cl.ecomarket.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;








@RestController
@RequestMapping("/api/v1/ecomarket/producto")
@Tag(name = "Producto Externo", description = "Controlador para gestionar productos externos")
public class ProductoExternoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProdExtrerAssembler userProdExtrerAssembler;

    @GetMapping()
@Operation(summary = "Listar productos externos", description = "Obtiene una lista de todos los productos externos disponibles")
public ResponseEntity<CollectionModel<EntityModel<ProductoDto>>> listarProductos() {
    List<ProductoDto> productos = productoService.listarProductos();
    if (productos == null || productos.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
   List<EntityModel<ProductoDto>> productoModels = productos.stream()
    .map(userProdExtrerAssembler::toModelProducto)
    .toList();
    return ResponseEntity.ok(CollectionModel.of(productoModels));
}

    @GetMapping("/userproductodto/{id}")
@Operation(summary = "Obtener producto por ID de usuario", description = "Obtiene un producto externo asociado a un usuario por su ID")
public ResponseEntity<EntityModel<UserProductoDto>> obtenerProductoPorIduser(@PathVariable("id") Long id) {
    ProductoDto producto = productoService.obtenerProductoPorId(id);
    if (producto == null) {
        return ResponseEntity.notFound().build();
    }
    User user;
    try {
        user = userService.userId(id.intValue());
    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
    UserProductoDto userProductoDto = new UserProductoDto();
    userProductoDto.setProducto(producto);
    userProductoDto.setUser(user);

    // Aplica el assembler para HATEOAS
    EntityModel<UserProductoDto> model = userProdExtrerAssembler.toModelUserProd(userProductoDto);
    return ResponseEntity.ok(model);
}
}
