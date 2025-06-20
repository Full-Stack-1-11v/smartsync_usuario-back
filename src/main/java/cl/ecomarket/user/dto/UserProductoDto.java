package cl.ecomarket.user.dto;

import cl.ecomarket.user.model.User;
import lombok.Data;

/**
 * DTO que representa la relación entre un usuario y un producto externo.
 * Contiene la información del usuario y del producto asociado.
 */
@Data
public class UserProductoDto {
    /**
     * Usuario asociado al producto.
     */
    private User user;
    /**
     * Producto asociado al usuario.
     */
    private ProductoDto producto;

}
