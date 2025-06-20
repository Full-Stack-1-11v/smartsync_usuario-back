package cl.ecomarket.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ecomarket.user.model.Rol;
import cl.ecomarket.user.repository.RolRepository;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para la gestión de roles.
 * Proporciona métodos para listar, buscar, guardar y eliminar roles.
 */
@Service
@Transactional
public class RolService {
    private static final Logger logger = LoggerFactory.getLogger(RolService.class);

    @Autowired
    private RolRepository rolRepository;

    /**
     * Obtiene la lista de todos los roles.
     * @return Lista de roles.
     */
    public List<Rol> listaList() {
        logger.info("RolService Listando todos los roles ");
        return rolRepository.findAll();
    }

    /**
     * Busca un rol por su ID.
     * @param id ID del rol.
     * @return Rol encontrado.
     * @throws RuntimeException si no se encuentra el rol.
     */
    public Rol findById(Integer id) {
        logger.info("RolService Buscando rol con ID: {}", id);
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }

    /**
     * Guarda un rol en la base de datos.
     * @param rol Rol a guardar.
     * @return Rol guardado.
     */
    public Rol save(Rol rol) {
        logger.info("RolService Guardando rol: {}", rol.getNombreRol());
        return rolRepository.save(rol);
    }

    /**
     * Elimina un rol por su ID.
     * @param id ID del rol a eliminar.
     */
    public void deleteById(Integer id) {
        logger.info("RolService Eliminando rol con ID: {}", id);
        rolRepository.deleteById(id);
    }
}