package cl.ecomarket.user.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cl.ecomarket.user.dto.UserProductoDto;
import jakarta.transaction.Transactional;


/**
 * Servicio para la gestión de productos asociados a usuarios.
 * Proporciona métodos para listar, buscar, guardar y eliminar productos de usuario.
 */
@Transactional
@Service
public class UserProductoDtoService {
    private static final Logger logger = LoggerFactory.getLogger(UserProductoDtoService.class);

    /**
     * Obtiene la lista de todos los productos de usuario.
     */
    public List<UserProductoDto> listaList() {
        logger.info("UserProductoDtoService Listando todos los productos de usuario");
        return null;
    }
    
    /**
     * Busca un producto de usuario por su ID.
     * @param id ID del producto de usuario.
     */
    public UserProductoDto findById(Integer id){
        logger.info("UserProductoDtoService Buscando producto de usuario con ID: {}", id);
        return null;
    }

    /**
     * Guarda un producto de usuario.
     * @param userProductoDto Producto de usuario a guardar.
     */ 
    public UserProductoDto save(UserProductoDto userProductoDto) {
        logger.info("UserProductoDtoService Guardando producto de usuario: {}", userProductoDto);
        return null;
    }

    /**
     * Elimina un producto de usuario por su ID.
     * @param id ID del producto de usuario a eliminar.
     */
    public void deleteById(Integer id) {
        logger.info("UserProductoDtoService Eliminando producto de usuario con ID: {}", id);
        
    }
    
}
