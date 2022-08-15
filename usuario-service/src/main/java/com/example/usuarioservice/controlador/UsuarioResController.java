package com.example.usuarioservice.controlador;

import com.example.usuarioservice.entidades.Usuario;
import com.example.usuarioservice.modelos.Carro;
import com.example.usuarioservice.modelos.Moto;
import com.example.usuarioservice.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioResController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.getAll();
        if(usuarios.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        if(usuario == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/carros/{usuarioId}")
    public ResponseEntity<List<Carro>> getCarros(@PathVariable("usuarioId") int usuarioId){
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if(usuario == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Carro> carros = usuarioService.getCarros(usuarioId);
        return new ResponseEntity<>(carros, HttpStatus.OK);
    }

    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<Moto>> getMotos(@PathVariable("usuarioId") int usuarioId){
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if(usuario == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Moto> motos = usuarioService.getMotos(usuarioId);
        return new ResponseEntity<>(motos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.OK);
    }

    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<Carro> guardarCarro(
            @PathVariable("usuarioId") int usuarioId,
            @RequestBody Carro carro
    ) {
        Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
        return new ResponseEntity<>(carro, HttpStatus.OK);
    }

    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMoto(
            @PathVariable("usuarioId") int usuarioId,
            @RequestBody Moto moto
    ){
        Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);
        return new ResponseEntity<>(moto, HttpStatus.OK);
    }

    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(
            @PathVariable("usuarioId") int usuarioId
    ){
        Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}
