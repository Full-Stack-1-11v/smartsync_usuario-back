package cl.ecomarket.user.dto;

import cl.ecomarket.user.model.User;
import lombok.Data;

@Data
public class UserProductoDto {
    
    private User user;
    private ProductoDto producto;

    
}
