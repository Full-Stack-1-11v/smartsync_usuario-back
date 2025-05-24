package cl.ecomarket.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ecomarket.user.model.Rol;
import cl.ecomarket.user.repository.RolRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    // Este método busca por el nombre del rol y devuelve una lista de roles
    public List<Rol> listaList() {
        return rolRepository.findAll();
    }
    
    // buscar por id
    public Rol findById(Integer id){
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }

    // agregar rol
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    // eliminar rol
    public void deleteById(Integer id) {
        rolRepository.deleteById(id);   
    }
}