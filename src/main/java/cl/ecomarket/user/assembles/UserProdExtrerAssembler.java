package cl.ecomarket.user.assembles;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import cl.ecomarket.user.controller.ProductoExternoController;
import cl.ecomarket.user.dto.ProductoDto;
import cl.ecomarket.user.dto.UserProductoDto;
import io.micrometer.common.lang.NonNull;

/**
 * Assembler para convertir objetos UserProductoDto y ProductoDto en EntityModel
 * con enlaces HATEOAS.
 */
@Component
public class UserProdExtrerAssembler {

    /**
     * Convierte un UserProductoDto en un EntityModel con enlace a la lista de
     * productos.
     * 
     * @param userProductoDto DTO de usuario y producto.
     * @return EntityModel con enlace HATEOAS.
     */
    @NonNull
    public EntityModel<UserProductoDto> toModelUserProd(@NonNull UserProductoDto userProductoDto) {
        return EntityModel.of(
                userProductoDto,
                linkTo(methodOn(ProductoExternoController.class).listarProductos()).withRel("listarProductos"));
    }

    /**
     * Convierte un ProductoDto en un EntityModel con enlace a sí mismo.
     * 
     * @param productoDto DTO del producto.
     * @return EntityModel con enlace HATEOAS.
     */
    @NonNull
    public EntityModel<ProductoDto> toModelProducto(@NonNull ProductoDto productoDto) {
        return EntityModel.of(
                productoDto,
                linkTo(methodOn(ProductoExternoController.class).listarProductos()).withSelfRel());
    }

}
