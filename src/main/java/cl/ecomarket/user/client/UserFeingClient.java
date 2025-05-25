package cl.ecomarket.user.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "monitoreo-api", url = "https://smartsyn-monitoreo-back-prueba.onrender.com")
public interface UserFeingClient {

    @GetMapping("/reporte/enviarMonitoreo")
    String recibirMonitoreo(String monitoreoDTO, String user);

}
