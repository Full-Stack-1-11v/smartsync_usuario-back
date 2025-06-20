package cl.ecomarket.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = "cl.ecomarket.user")
public class UserApplication {

	/*
	 * Método principal para iniciar la aplicación.
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
		
	}

}
