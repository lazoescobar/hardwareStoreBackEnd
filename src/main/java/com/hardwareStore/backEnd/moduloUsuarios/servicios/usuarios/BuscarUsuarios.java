package com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios;

import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.UsuarioEntidad;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.UsuarioRepository;

public class BuscarUsuarios {
    private final UsuarioRepository usuarioRepository;

    public BuscarUsuarios(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public static class EntradaBuscarUsuarios {
        public Integer  idUsuario;
        public String nombre;
    }


    //Salida servicio
    public class SalidaBuscarUsuarios extends SalidaBaseService {
    }


    //Logica
    public SalidaBuscarUsuarios logica(EntradaBuscarUsuarios entrada){

        Integer idUsuario = entrada.idUsuario;
        String nombreUsuario = entrada.nombre;
        SalidaBuscarUsuarios salida = new SalidaBuscarUsuarios();
        if((idUsuario != null && idUsuario != 0) && (nombreUsuario != null && !nombreUsuario.isEmpty())){
            UsuarioEntidad usuarioEntidad = usuarioRepository.findFirstByIdAndNombre(idUsuario, nombreUsuario);
            if(usuarioEntidad == null){

            }
        }





        /*
        BuscarTiposUsuarios.Salida salida = new BuscarTiposUsuarios.Salida();
        List<TipoUsuario> tipoUsuarios = tipoUsuarioRepository.findAll();
        if(!tipoUsuarios.isEmpty()){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setErrores(+1);
            salida.setMensaje("Tipos usuarios no encontrados");
            return salida;
        }

        List<BuscarTiposUsuarios.TipoUsuarioMapper> tipos = new ArrayList<>();
        for(TipoUsuario tipoUsuario: tipoUsuarios){
            BuscarTiposUsuarios.TipoUsuarioMapper tipo = new BuscarTiposUsuarios.TipoUsuarioMapper();
            tipo.id = tipoUsuario.getId();
            tipo.tipo = tipoUsuario.getTipo();
            salida.tiposUsuarios.add(tipo);
        }

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Tipos usuarios");
        */
        return salida;
    }

}
