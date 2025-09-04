package cl.ecomarket.user.controller;

import cl.ecomarket.user.client.ProductoService;
import cl.ecomarket.user.dto.ProductoDto;
import cl.ecomarket.user.model.User;
import cl.ecomarket.user.service.RolService;
import cl.ecomarket.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductoExternoController.class)
public class ProductoExternoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;
    @MockBean
    private ProductoService productoService;
    @MockBean
    private UserService userService;

    @Test
    void testListarProductosOk() throws Exception {
        ProductoDto producto = new ProductoDto();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Producto1");
        Mockito.when(productoService.listarProductos()).thenReturn(java.util.List.of(producto));

        mockMvc.perform(get("/api/v1/ecomarket/producto"))
                .andExpect(status().isOk())
                // Ajuste para HATEOAS: busca en _embedded.productoDtoList
                .andExpect(jsonPath("$._embedded.productoDtoList[0].idProducto").value(1))
                .andExpect(jsonPath("$._embedded.productoDtoList[0].nombreProducto").value("Producto1"));
    }

    @Test
    void testListarProductosNull() throws Exception {
        Mockito.when(productoService.listarProductos()).thenReturn(null);

        mockMvc.perform(get("/api/v1/ecomarket/producto"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testObtenerProductoPorIduserOk() throws Exception {
        ProductoDto producto = new ProductoDto();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Producto1");
        User user = new User();
        user.setId(1);
        user.setName("Usuario1");

        Mockito.when(productoService.obtenerProductoPorId(1L)).thenReturn(producto);
        Mockito.when(userService.userId(1)).thenReturn(user);

        mockMvc.perform(get("/api/v1/ecomarket/producto/userproductodto/1"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.producto.idProducto").value(1L))
    .andExpect(jsonPath("$.user.id").value(1));
    }

    @Test
    void testObtenerProductoPorIduserProductoNotFound() throws Exception {
        Mockito.when(productoService.obtenerProductoPorId(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/ecomarket/producto/userproductodto/1"))
                .andExpect(status().isNotFound());
    }


    @Test
void testListarProductosEmpty() throws Exception {
    Mockito.when(productoService.listarProductos()).thenReturn(java.util.Collections.emptyList());

    mockMvc.perform(get("/api/v1/ecomarket/producto"))
            .andExpect(status().isNoContent());
} 
    @Test
    void testObtenerProductoPorIduserUserNotFound() throws Exception {
        ProductoDto producto = new ProductoDto();
        producto.setIdProducto(1L);
        Mockito.when(productoService.obtenerProductoPorId(1L)).thenReturn(producto);
        Mockito.when(userService.userId(1)).thenThrow(new RuntimeException("No encontrado"));

        mockMvc.perform(get("/api/v1/ecomarket/producto/userproductodto/1"))
                .andExpect(status().isNotFound());
    }
}