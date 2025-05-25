package cl.ecomarket.user.dto;

public class ProductoDto {
    private Long idProducto;
    private String nombreProducto;
    private int precioProducto;
    private int stockProducto;

    public ProductoDto() {
    }
    public ProductoDto(Long idProducto, String nombreProducto, int precioProducto, int stockProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.stockProducto = stockProducto;
    }
    public Long getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }
    public String getNombreProducto() {
        return nombreProducto;
    }
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    public int getPrecioProducto() {
        return precioProducto;
    }
    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }
    public int getStockProducto() {
        return stockProducto;
    }
    public void setStockProducto(int stockProducto) {
        this.stockProducto = stockProducto;
    }


}
