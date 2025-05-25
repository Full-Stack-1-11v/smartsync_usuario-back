package cl.ecomarket.user.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.ecomarket.user.dto.MonitoreoDTO;


@FeignClient(name = "monitoreo-api", url = "https://smartsyn-monitoreo-back-prueba.onrender.com")
public interface UserFeingClient {

   
}
