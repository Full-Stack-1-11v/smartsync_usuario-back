package cl.ecomarket.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa los datos básicos de un usuario.
 * Incluye identificador, nombre, email y estado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    /**
     * Identificador único del usuario.
     */
    private Integer id;
    /**
     * Nombre del usuario.
     */
    private String name;
    /**
     * Correo electrónico del usuario.
     */
    private String email;
    /**
     * Estado del usuario (activo/inactivo).
     */
    private boolean estado;
}
