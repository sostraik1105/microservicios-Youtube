package com.example.usuarioservice.servicio;

import com.example.usuarioservice.controlador.MotoFeignClient;
import com.example.usuarioservice.entidades.Usuario;
import com.example.usuarioservice.feignClients.CarroFeignClient;
import com.example.usuarioservice.modelos.Carro;
import com.example.usuarioservice.modelos.Moto;
import com.example.usuarioservice.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {
    private static final String URL_CARRO = "http://localhost:8002/carro";
    private static final String URL_MOTO = "http://localhost:8003/moto";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestTemplate restTemplate; // Permite realizar la comunicación entre microservicios

    @Autowired
    private CarroFeignClient carroFeignClient; // comunicación entre microservicios con feignClient

    @Autowired
    private MotoFeignClient motoFeignClient;

    public List<Carro> getCarros(int usuarioId){
        List<Carro> carros = restTemplate.getForObject(URL_CARRO + "/usuario/" + usuarioId, List.class);
        return carros;
    }

    public List<Moto> getMotos(int usuarioId){
        List<Moto> motos = restTemplate.getForObject(URL_MOTO + "/usuario/" + usuarioId, List.class);
        return motos;
    }

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario){
        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        return nuevoUsuario;
    }

    // FEIGN CLIENT

    public Carro saveCarro(int usuarioId, Carro carro){
        carro.setUsuarioId(usuarioId);
        Carro nuevoCarro = carroFeignClient.save(carro);
        return nuevoCarro;
    }
    public Moto saveMoto(int usuarioId, Moto moto){
        moto.setUsuarioId(usuarioId);
        Moto nuevaMoto = motoFeignClient.save(moto);
        return nuevaMoto;
    }

    public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){
        Map<String, Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if(usuario == null){
            resultado.put("Mensaje", "El usuario no existe");
            return resultado;
        }

        resultado.put("Usuario", usuario);

        List<Carro> carros = carroFeignClient.getCarros(usuarioId);
        if(carros.isEmpty()){
            resultado.put("Carros", "El usuario no tiene carros");
        } else {
            resultado.put("Carros", carros);
        }

        List<Moto> motos = motoFeignClient.getMotos(usuarioId);
        if(motos.isEmpty()){
            resultado.put("Motos", "El usuario no tiene motos");
        } else {
            resultado.put("Motos", motos);
        }

        return resultado;
    }
}
