package cl.ecomarket.user.dto;
 /**
 * DTO que representa los datos de un producto externo.
 * Incluye identificador, nombre, precio y stock del producto.
 */
public class ProductoDto {
    /*
     * aca va el id, nombre,precio y stock del producto 
    */
    private Long idProducto;
    private String nombreProducto;
    private int precioProducto;
    private int stockProducto;

   
    public ProductoDto() {
    }

    /**
     * Constructor con todos los atributos.
     * @param idProducto Identificador del producto.
     * @param nombreProducto Nombre del producto.
     * @param precioProducto Precio del producto.
     * @param stockProducto Stock disponible del producto.
     */
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
