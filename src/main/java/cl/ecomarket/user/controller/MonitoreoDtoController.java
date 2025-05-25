package cl.ecomarket.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ecomarket.user.dto.MonitoreoDTO;
import cl.ecomarket.user.dto.MonitoreoUserRequest;
import cl.ecomarket.user.model.User;



@RestController
@RequestMapping("/api/v1/monitoreo")
public class MonitoreoDtoController {
 
    @PostMapping("/enviarMonitoreo")
public ResponseEntity<String> recibirMonitoreo(@RequestBody MonitoreoDTO monitoreoDTO) {
    // Aquí puedes visualizar o procesar los datos recibidos
    System.out.println(monitoreoDTO.getDescripcion());
    return ResponseEntity.ok("Monitoreo recibido correctamente");
}
    
}
