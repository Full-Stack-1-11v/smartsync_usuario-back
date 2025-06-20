package cl.ecomarket.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un rol de usuario en el sistema.
 * Cada rol tiene un identificador único y un nombre.
 */
@Entity
@Table(name = "rol")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    /**
     * Identificador único del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_rol;
    /**
     * Nombre del rol.
     */
    @Column(name = "nombre_rol", nullable = false)
    private String nombreRol;

}
