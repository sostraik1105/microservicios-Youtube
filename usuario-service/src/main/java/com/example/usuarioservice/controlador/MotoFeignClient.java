package com.example.usuarioservice.controlador;

import com.example.usuarioservice.modelos.Moto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "moto-service", url = "http://localhost:8003")
@RequestMapping("/moto")
public interface MotoFeignClient {

    @PostMapping()
    public Moto save(@RequestBody Moto moto);

    @GetMapping("/usuario/{usuarioId}")
    public List<Moto> getMotos(@PathVariable("usuarioId") int usuarioId);
}
