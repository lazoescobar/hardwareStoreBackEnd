package com.hardwareStore.backEnd.moduloUsuarios.servicios.tipoUsuarios;


import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.TipoUsuario;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.TipoUsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuscarTiposUsuarios {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    public BuscarTiposUsuarios(TipoUsuarioRepository tipoUsuarioRepository){
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    //Entrada servicio
    public class Entrada {}


    //Mappers
    public class TipoUsuarioMapper {
        public String id;
        public String tipo;
    }


    //Salida servicio
    public class Salida extends SalidaBaseService{
        List<TipoUsuarioMapper> tiposUsuarios = new ArrayList<>();

        public List<TipoUsuarioMapper> getTiposUsuarios() {
            return tiposUsuarios;
        }
    }


    //Logica
    public Salida logica(Entrada entrada){
        Salida salida = new Salida();
        List<TipoUsuario> tipoUsuarios = tipoUsuarioRepository.findAll();
        if(!tipoUsuarios.isEmpty()){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setErrores(+1);
            salida.setMensaje("Tipos usuarios no encontrados");
            return salida;
        }

        List<TipoUsuarioMapper> tipos = new ArrayList<>();
        for(TipoUsuario tipoUsuario: tipoUsuarios){
            TipoUsuarioMapper tipo = new TipoUsuarioMapper();
            tipo.id = tipoUsuario.getId();
            tipo.tipo = tipoUsuario.getTipo();
            salida.tiposUsuarios.add(tipo);
        }

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Tipos usuarios");

        return salida;
    }
}
