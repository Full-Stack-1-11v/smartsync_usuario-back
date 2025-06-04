package cl.ecomarket.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ecomarket.user.client.ProductoService;
import cl.ecomarket.user.dto.ProductoDto;

import cl.ecomarket.user.dto.UserProductoDto;
import cl.ecomarket.user.model.User;
import cl.ecomarket.user.service.UserService;








@RestController
@RequestMapping("/api/v1/ecomarket/producto")
public class ProductoExternoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<ProductoDto> listarProductos() {
        List<ProductoDto> productos = productoService.listarProductos();
        if (productos == null) {
            return new ArrayList<>();
        }
        return productos;
    }

    @GetMapping("/userproductodto/{id}")
public ResponseEntity<UserProductoDto> obtenerProductoPorIduser(@PathVariable("id") Long id) {
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
    return ResponseEntity.ok(userProductoDto);
}
}
