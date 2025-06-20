package cl.ecomarket.user.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.ecomarket.user.model.Rol;
/**
 * Repositorio para la entidad Rol.
 * Proporciona métodos para acceder y consultar roles en la base de datos.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
   
    }

