package cl.ecomarket.user.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.ecomarket.user.dto.ProductoDto;


@FeignClient(name = "producto-api", url = "https://smartsync-producto-back.onrender.com/")
public interface ProductoServiceInterfaz {

     @GetMapping("/api/v1/ecomarket/producto/{id}")
    ProductoDto obtenerProductoPorId(@PathVariable("id") Long id);

    @GetMapping("/api/v1/ecomarket/producto")
    java.util.List<ProductoDto> listarProductos();
   
}
