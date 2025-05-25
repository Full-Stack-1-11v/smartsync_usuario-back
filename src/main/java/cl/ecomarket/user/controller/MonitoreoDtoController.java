package cl.ecomarket.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ecomarket.user.dto.MonitoreoDTO;




@RestController
@RequestMapping("/api/v1/monitoreo")
public class MonitoreoDtoController {
 private final List<MonitoreoDTO> monitoreos = new ArrayList<>();
    @PostMapping("/enviarMonitoreo")
public ResponseEntity<String> recibirMonitoreo(@RequestBody MonitoreoDTO monitoreoDTO) {
    // Aquí puedes visualizar o procesar los datos recibidos
    System.out.println(monitoreoDTO.getDescripcion());

    return ResponseEntity.ok("Monitoreo recibido correctamente");
}






    @GetMapping("/todos")
    public List<MonitoreoDTO> obtenerTodos() {
        return monitoreos; // Devuelve todos los monitoreos recibidos
    }
    
}
