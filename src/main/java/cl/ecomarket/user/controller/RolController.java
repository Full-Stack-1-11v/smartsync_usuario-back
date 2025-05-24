package cl.ecomarket.user.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ecomarket.user.model.Rol;
import cl.ecomarket.user.service.RolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/rol")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("/listar")
    public ResponseEntity<List<Rol>> listarRoles() {
        List<Rol> roles = rolService.listaList();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(roles);
        }
    }
    


  @GetMapping("/buscar/{id}")
    public ResponseEntity<Rol> buscarRolPorId(@PathVariable Integer id) {
    try {
        Rol rol = rolService.findById(id);
        return ResponseEntity.ok(rol);
    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
    
}  

    // agregar rol
    @PostMapping("/agregar")
    public ResponseEntity<Rol> agregarRol(@RequestBody Rol rol) {
        try {
            Rol nuevoRol = rolService.save(rol);
            return ResponseEntity.ok(nuevoRol);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
 
 


}
