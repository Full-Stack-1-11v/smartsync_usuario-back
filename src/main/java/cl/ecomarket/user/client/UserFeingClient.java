package cl.ecomarket.user.client;


import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "user-api", url = "https://smartsync-usuario-back-pruebas.onrender.com")
public interface UserFeingClient {


}
