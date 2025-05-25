package cl.ecomarket.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ecomarket.user.client.ProductoServiceInterfaz;
import cl.ecomarket.user.dto.ProductoDto;
import cl.ecomarket.user.dto.UserDto;







@RestController
@RequestMapping("/api/v1/ecomarket/producto")
public class ProductoExternoController {

    @Autowired
    private ProductoServiceInterfaz productoService;

    @GetMapping()
    public List<ProductoDto> listarProductos() {
        List<ProductoDto> productos = productoService.listarProductos();
        if (productos == null) {
            return new ArrayList<>();
        }
        return productos;
    }

    @GetMapping("/{id}")
public ProductoDto obtenerProductoPorId(@PathVariable("id") Long id) {
    return productoService.obtenerProductoPorId(id);
}

}
