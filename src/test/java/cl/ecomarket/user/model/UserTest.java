package cl.ecomarket.user.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UserTest {
   @Test
    void testGettersAndSetters () {
        User user = new User();
        Rol rol = new Rol();
        Rol rol1 = new Rol();
        Rol rol2 = new Rol();

        user.setId(1);
        user.setName("juan");
        user.setEmail("@juan");
        user.setPassword("1234");
        user.setEstado(true);

        user.setRol(rol);
        assertEquals(rol, user.getRol());

        user.setRol(rol1);
        assertEquals(rol1, user.getRol());

        user.setRol(rol2);
        assertEquals(rol2, user.getRol());

        assertEquals(1, user.getId());
        assertEquals("juan", user.getName());
        assertEquals("@juan", user.getEmail());
        assertEquals("1234", user.getPassword());
        assertTrue(user.isEstado());
    }


    @Test
    void testEqualsAndHashCode() {
        User user1 = new User(1, "juan", "@juan", "1234", true, null);
        User user2 = new User(1, "juan", "@juan", "1234", true, null); 
        User user3 = new User(2, "maria", "@maria", "5678", false, null);

        // equals
        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        

        // hashCode
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());

    }
        @Test
        void testToString() {
            User user = new User(1, "juan", "@juan", "1234", true, null);
            String srt = user.toString();
            assertNotNull(srt);
            assertTrue(srt.contains("juan"));
    }


    @Test
    void testCanEqual() {
        User user = new User();
        assertTrue(user.canEqual(new User()));
        assertFalse(user.canEqual("not a user"));
    }

    @Test
    void testHashCodeBranches() {
        User user1 = new User(1, "juan", "@juan", "1234", true, null);
        User user2 = new User(1, "juan", "@juan", "1234", true, null);
        User user3 = new User(2, "ana", "@ana", "abcd", false, null);

        // Igual a sí mismo
        assertEquals(user1.hashCode(), user1.hashCode());

        // Igual a otro con mismos datos
        assertEquals(user1.hashCode(), user2.hashCode());

        // Distinto por datos
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }
   
    @Test
    void testEqualsBranches() {
        User user1 = new User(1, "juan", "@juan", "1234", true, null);
        User user2 = new User(1, "juan", "@juan", "1234", true, null);
        User user3 = new User(2, "ana", "@ana", "abcd", false, null);

        // Igual a sí mismo
        assertEquals(user1, user1);

        // Igual a otro con mismos datos
        assertEquals(user1, user2);

        // Distinto por datos
        assertNotEquals(user1, user3);

        // Comparar con null
        assertNotEquals(user1, null);

        // Comparar con otro tipo de objeto
        assertNotEquals(user1, "otro tipo");
    }

// nulos
    @Test
    void testEqualsWithNullFields() {
        User user1 = new User(null, null, null, null, false, null);
        User user2 = new User(null, null, null, null, false, null);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    // campos nulos
    @Test
    void testEqualsSelfWithNullFields() {
        User user = new User(null, null, null, null, false, null);
        assertEquals(user, user);
    }

    @Test
void testEqualsWithOneNullField() {
    User user1 = new User(1, null, "@juan", "1234", true, null);
    User user2 = new User(1, "juan", "@juan", "1234", true, null);

    assertNotEquals(user1, user2);
}
}



