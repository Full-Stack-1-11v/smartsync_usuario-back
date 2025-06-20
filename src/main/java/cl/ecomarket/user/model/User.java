package cl.ecomarket.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un usuario en el sistema.
 * Contiene información personal, credenciales y su rol asociado.
 */
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Nombre del usuario.
     */
    @Column(nullable = false)
    private String name;
    /**
     * Correo electrónico del usuario.
     */
    @Column(nullable = false)
    private String email;
    /**
     * Contraseña del usuario.
     */
    @Column(nullable = false)
    private String password;
    /**
     * Estado del usuario (activo/inactivo).
     */
    @Column(nullable = false)
    private boolean estado;
    /**
     * Rol asociado al usuario.
     */
    @ManyToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    private Rol rol;

}