package cl.ecomarket.user.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.ecomarket.user.dto.ProductoDto;

/**
 * Cliente Feign para consumir el servicio externo de productos.
 * Permite obtener productos por ID y listar todos los productos.
 */
@FeignClient(name = "producto-api", url = "https://smartsync-producto-back.onrender.com/")
public interface ProductoService {

    /**
     * Obtiene un producto externo por su ID.
     * @param id Identificador del producto.
     * @return ProductoDto correspondiente al ID.
     */
     @GetMapping("/api/v1/ecomarket/producto/{id}")
    ProductoDto obtenerProductoPorId(@PathVariable("id") Long id);

    /**
     * Lista todos los productos externos disponibles.
     * @return Lista de ProductoDto.
     */
    @GetMapping("/api/v1/ecomarket/producto")
    java.util.List<ProductoDto> listarProductos();
   
}
