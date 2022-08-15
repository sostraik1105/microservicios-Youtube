package com.example.motoservice.controlador;

import com.example.motoservice.entidades.Moto;
import com.example.motoservice.servicio.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moto")
public class MotoRestController {
    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> listarMotos(){
        List<Moto> motos = motoService.getAll();
        if(motos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(motos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> obtenerMoto(@PathVariable("id") int id){
        Moto moto = motoService.getMotoById(id);
        if(moto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(moto, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotosPorUsuarioId(@PathVariable int usuarioId){
        List<Moto> motos = motoService.byUsuarioId(usuarioId);
        if(motos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(motos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Moto> guardarMoto(@RequestBody Moto moto){
        Moto nuevaMoto = motoService.save(moto);
        return new ResponseEntity<>(nuevaMoto, HttpStatus.OK);
    }
}
