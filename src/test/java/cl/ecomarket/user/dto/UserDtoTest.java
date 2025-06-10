package cl.ecomarket.user.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserDtoTest {
      @Test
    void testAllArgsConstructorAndGetters () {
        UserDto user = new UserDto(1,"juan","@perez",true);
        assertEquals(1, user.getId());
        assertEquals("juan", user.getName());
        assertEquals("@perez", user.getEmail());
        assertEquals(true, user.isEstado());

    }
    @Test
    void testNoArgsConstructorAndSetters() {
        UserDto user = new UserDto();
        user.setId(2);
        user.setName("Ana");
        user.setEmail("ana@mail.com");
        user.setEstado(false);

        assertEquals(2, user.getId());
        assertEquals("Ana", user.getName());
        assertEquals("ana@mail.com", user.getEmail());
        assertFalse(user.isEstado());
    }

    @Test
    void testEqualsAndHashCode() {
        UserDto user1 = new UserDto(1, "Juan", "juan@mail.com", true);
        UserDto user2 = new UserDto(1, "Juan", "juan@mail.com", true);
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToString() {
        UserDto user = new UserDto(1, "Juan", "juan@mail.com", true);
        String str = user.toString();
        assertTrue(str.contains("Juan"));
        assertTrue(str.contains("juan@mail.com"));
    }

    @Test
void testNullFields() {
    UserDto user = new UserDto();
    assertNull(user.getId());
    assertNull(user.getName());
    assertNull(user.getEmail());
    // Para boolean, el valor por defecto es false
    assertFalse(user.isEstado());
}

@Test
void testAllArgsConstructorWithNulls() {
    UserDto user = new UserDto(null, null, null, false);
    assertNull(user.getId());
    assertNull(user.getName());
    assertNull(user.getEmail());
    assertFalse(user.isEstado());
}

@Test
void testEmptyAndInvalidValues() {
    UserDto user = new UserDto(-1, "", "correo-no-valido", true);

    assertEquals(-1, user.getId()); // valor negativo
    assertEquals("", user.getName()); // string vacío
    assertEquals("correo-no-valido", user.getEmail()); // email mal formado
    assertTrue(user.isEstado());
}

@Test
void testEqualsWithNull() {
    UserDto user = new UserDto(1, "Juan", "juan@mail.com", true);
    assertFalse(user.equals(null));
}

@Test
void testEqualsWithDifferentClass() {
    UserDto user = new UserDto(1, "Juan", "juan@mail.com", true);
    assertFalse(user.equals("otro tipo"));
}

@Test
void testEqualsWithItself() {
    UserDto user = new UserDto(1, "Juan", "juan@mail.com", true);
    assertTrue(user.equals(user));
}

@Test
void testEqualsWithDifferentValues() {
    UserDto user1 = new UserDto(1, "Juan", "juan@mail.com", true);
    UserDto user2 = new UserDto(2, "Ana", "ana@mail.com", false);
    assertFalse(user1.equals(user2));
}

@Test
void testEqualsWithNullFields() {
    UserDto user1 = new UserDto(null, null, null, false);
    UserDto user2 = new UserDto(null, null, null, false);
    assertTrue(user1.equals(user2));
    assertEquals(user1.hashCode(), user2.hashCode());
}

@Test
void testEqualsWithOneNullField() {
    UserDto user1 = new UserDto(1, null, "juan@mail.com", true);
    UserDto user2 = new UserDto(1, "Juan", "juan@mail.com", true);
    assertFalse(user1.equals(user2));
}

@Test
void testEqualsWithOnlyOneFieldDifferent() {
    UserDto user1 = new UserDto(1, "Juan", "juan@mail.com", true);
    UserDto user2 = new UserDto(1, "Juan", "juan@mail.com", false);
    assertFalse(user1.equals(user2));
}
}
