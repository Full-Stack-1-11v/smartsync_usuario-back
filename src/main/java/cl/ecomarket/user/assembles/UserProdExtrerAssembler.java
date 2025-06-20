package cl.ecomarket.user.assembles;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import cl.ecomarket.user.controller.ProductoExternoController;
import cl.ecomarket.user.dto.ProductoDto;
import cl.ecomarket.user.dto.UserProductoDto;
import io.micrometer.common.lang.NonNull;


@Component
public class UserProdExtrerAssembler {



    @NonNull
    public EntityModel<UserProductoDto> toModelUserProd(@NonNull UserProductoDto userProductoDto) {
        return EntityModel.of(
                userProductoDto,
                linkTo(methodOn(ProductoExternoController.class).listarProductos()).withRel("listarProductos")
                   );
    }

    @NonNull
public EntityModel<ProductoDto> toModelProducto(@NonNull ProductoDto productoDto) {
    return EntityModel.of(
        productoDto,
        linkTo(methodOn(ProductoExternoController.class).listarProductos()).withSelfRel()
    );
}

}
