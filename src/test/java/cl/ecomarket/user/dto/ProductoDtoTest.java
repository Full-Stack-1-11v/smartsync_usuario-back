package cl.ecomarket.user.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ProductoDtoTest {
    @Test
    void testConstructorAndGetters() {
        ProductoDto producto = new ProductoDto(1L, "Pan", 1000, 5);
        assertEquals(1L, producto.getIdProducto());
        assertEquals("Pan", producto.getNombreProducto());
        assertEquals(1000, producto.getPrecioProducto());
        assertEquals(5, producto.getStockProducto());
    }

    @Test
    void testSetters() {
        ProductoDto producto = new ProductoDto();
        producto.setIdProducto(2L);
        producto.setNombreProducto("Leche");
        producto.setPrecioProducto(1200);
        producto.setStockProducto(10);

        assertEquals(2L, producto.getIdProducto());
        assertEquals("Leche", producto.getNombreProducto());
        assertEquals(1200, producto.getPrecioProducto());
        assertEquals(10, producto.getStockProducto());
    }

    @Test
    void testNullValues() {
        ProductoDto producto = new ProductoDto();
        producto.setIdProducto(null);
        producto.setNombreProducto(null);

        assertNull(producto.getIdProducto());
        assertNull(producto.getNombreProducto());
        assertEquals(0, producto.getPrecioProducto());
        assertEquals(0, producto.getStockProducto());
    }

    @Test
    void testNegativeValues() {
        ProductoDto producto = new ProductoDto(-1L, "", -100, -5);
        assertEquals(-1L, producto.getIdProducto());
        assertEquals("", producto.getNombreProducto());
        assertEquals(-100, producto.getPrecioProducto());
        assertEquals(-5, producto.getStockProducto());
    }

    @Test
    void testRepeatedValues() {
        ProductoDto p1 = new ProductoDto(1L, "Pan", 1000, 5);
        ProductoDto p2 = new ProductoDto(1L, "Pan", 1000, 5);

        assertEquals(p1.getIdProducto(), p2.getIdProducto());
        assertEquals(p1.getNombreProducto(), p2.getNombreProducto());
        assertEquals(p1.getPrecioProducto(), p2.getPrecioProducto());
        assertEquals(p1.getStockProducto(), p2.getStockProducto());
    }

    @Test
    void testDifferentValues() {
        ProductoDto p1 = new ProductoDto(1L, "Pan", 1000, 5);
        ProductoDto p2 = new ProductoDto(2L, "Leche", 1200, 10);

        assertNotEquals(p1.getIdProducto(), p2.getIdProducto());
        assertNotEquals(p1.getNombreProducto(), p2.getNombreProducto());
        assertNotEquals(p1.getPrecioProducto(), p2.getPrecioProducto());
        assertNotEquals(p1.getStockProducto(), p2.getStockProducto());
    }
}
