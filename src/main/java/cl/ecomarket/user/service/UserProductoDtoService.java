package cl.ecomarket.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.ecomarket.user.dto.UserProductoDto;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class UserProductoDtoService {

    // Este método busca por el nombre del rol y devuelve una lista de roles
    public List<UserProductoDto> listaList() {
        return null;
    }
    
    // buscar por id
    public UserProductoDto findById(Integer id){
        return null;
    }

    // agregar rol
    public UserProductoDto save(UserProductoDto userProductoDto) {
        return null;
    }

    // eliminar rol
    public void deleteById(Integer id) {
        
    }
    
}
