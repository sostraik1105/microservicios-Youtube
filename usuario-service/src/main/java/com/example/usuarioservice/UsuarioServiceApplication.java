package com.example.usuarioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients // activa la comunicaciVn con microservicios
@SpringBootApplication
public class UsuarioServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuarioServiceApplication.class, args);
    }

}
