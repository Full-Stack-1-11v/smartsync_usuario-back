package cl.ecomarket.user.dto;

import cl.ecomarket.user.model.User;

public class UserProductoDto {
    
    private User user;
    private ProductoDto producto;

    
    public UserProductoDto() {
    }
    public UserProductoDto(User user, ProductoDto producto) {
        this.user = user;
        this.producto = producto;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public ProductoDto getProducto() {
        return producto;
    }
    public void setProducto(ProductoDto producto) {
        this.producto = producto;
    }




    

    
}
