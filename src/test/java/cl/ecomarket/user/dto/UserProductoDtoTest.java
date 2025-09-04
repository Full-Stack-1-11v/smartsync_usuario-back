package cl.ecomarket.user.dto;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import cl.ecomarket.user.model.User;



@SpringBootTest
@ActiveProfiles("test")
public class UserProductoDtoTest {
   
@Test
    void testAllArgsConstructorAndGetters() {
        User user = new User();
        ProductoDto producto = new ProductoDto(1L, "Pan", 1000, 5);
        UserProductoDto dto = new UserProductoDto();
        dto.setUser(user);
        dto.setProducto(producto);

        assertEquals(user, dto.getUser());
        assertEquals(producto, dto.getProducto());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        UserProductoDto dto = new UserProductoDto();
        assertNull(dto.getUser());
        assertNull(dto.getProducto());

        User user = new User();
        ProductoDto producto = new ProductoDto(2L, "Leche", 1200, 10);

        dto.setUser(user);
        dto.setProducto(producto);

        assertEquals(user, dto.getUser());
        assertEquals(producto, dto.getProducto());
    }

    @Test
    void testNullValues() {
        UserProductoDto dto = new UserProductoDto();
        dto.setUser(null);
        dto.setProducto(null);
        assertNull(dto.getUser());
        assertNull(dto.getProducto());
    }

    @Test
    void testRepeatedValues() {
        User user = new User();
        ProductoDto producto = new ProductoDto(1L, "Pan", 1000, 5);
        UserProductoDto dto1 = new UserProductoDto();
        dto1.setUser(user);
        dto1.setProducto(producto);

        UserProductoDto dto2 = new UserProductoDto();
        dto2.setUser(user);
        dto2.setProducto(producto);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testDifferentValues() {
        User user1 = new User();
        User user2 = new User();
        ProductoDto producto1 = new ProductoDto(1L, "Pan", 1000, 5);
        ProductoDto producto2 = new ProductoDto(2L, "Leche", 1200, 10);

        UserProductoDto dto1 = new UserProductoDto();
        dto1.setUser(user1);
        dto1.setProducto(producto1);

        UserProductoDto dto2 = new UserProductoDto();
        dto2.setUser(user2);
        dto2.setProducto(producto2);

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEqualsWithNull() {
        UserProductoDto dto = new UserProductoDto();
        assertNotEquals(dto, null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        UserProductoDto dto = new UserProductoDto();
        assertNotEquals(dto, "otro tipo");
    }

    @Test
    void testEqualsWithItself() {
        UserProductoDto dto = new UserProductoDto();
        assertEquals(dto, dto);
    }

    @Test
    void testToString() {
        User user = new User();
        ProductoDto producto = new ProductoDto(1L, "Pan", 1000, 5);
        UserProductoDto dto = new UserProductoDto();
        dto.setUser(user);
        dto.setProducto(producto);

        String str = dto.toString();
        assertTrue(str.contains("user"));
        assertTrue(str.contains("producto"));
    }
   
}


