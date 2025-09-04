package cl.ecomarket.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class RolTest {

    @Test
    void testGettersAndSetters() {
        Rol rol = new Rol();

        rol.setId_rol(1);
        rol.setNombreRol("Administrador");

        assertEquals(1, rol.getId_rol());
        assertEquals("Administrador", rol.getNombreRol());
        
    }

    @Test
    void testEqualsAndHashCode() {
        Rol rol1 = new Rol(1,"Administrador");
        Rol rol2 = new Rol(1,"Administrador");
        Rol rol3 = new Rol(2,"Usuario");
        
        // equals
        assertEquals(rol1,rol2);
        assertNotEquals(rol1, rol3);

        // hashCode
        assertEquals(rol1.hashCode(),rol2.hashCode());
        assertNotEquals(rol1.hashCode(), rol3.hashCode());
    }
    
    
}
