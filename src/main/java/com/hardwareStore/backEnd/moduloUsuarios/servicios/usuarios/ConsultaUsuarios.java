package com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.Producto;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.ConsultarProductos;
import com.hardwareStore.backEnd.moduloPersona.persistencias.modelos.Persona;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaUsuarios {

    private UsuarioRepository usuarioRepository;

    public ConsultaUsuarios(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public static class EntradaConsultarUsuarios{
        public String nombre;
        public Boolean todos;
    }

    public class UsuarioConsulta {
        public Integer id;
        public String nombreUsuario;
        public String nombreCompleto;
        public String perfil;
        public String fechaRegistro;
        public String estado;
    }


    public class SalidaConsultarUsuarios extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public List<UsuarioConsulta> usuarios;

        public List<UsuarioConsulta> getUsuarios() {
            return usuarios;
        }

        public void setUsuarios(List<UsuarioConsulta> usuarios) {
            this.usuarios = usuarios;
        }
    }


    public SalidaConsultarUsuarios logica(EntradaConsultarUsuarios entrada){

        SalidaConsultarUsuarios salida = new SalidaConsultarUsuarios();
        boolean busquedaRealizada = false;
        boolean existenUsuarios = false;

        List<Usuario> usuarios = new ArrayList<>();

        if((entrada.nombre == null || entrada.nombre.isEmpty()) && (entrada.todos == null || !entrada.todos)){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje("No se encontaron usuarios");
            salida.setErrores(+1);
            return  salida;
        }

        if(entrada.todos){
            usuarios.addAll(usuarioRepository.findAll());
            if(!usuarios.isEmpty()){
                existenUsuarios = true;
            }
            busquedaRealizada = true;
        }

        if(!busquedaRealizada){
            usuarios.addAll(usuarioRepository.findByCaseInsensitivNombre(entrada.nombre));
            if(!usuarios.isEmpty()){
                existenUsuarios = true;
            }
        }

        if(!existenUsuarios){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje("No se encontaron productos");
            salida.setErrores(+1);
            return  salida;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<UsuarioConsulta> usuariosConsulta = new ArrayList<>();
        for(Usuario usuario: usuarios){
            UsuarioConsulta usuarioConsulta = new UsuarioConsulta();
            usuarioConsulta.id = usuario.getId();
            usuarioConsulta.nombreUsuario = usuario.getNombre();
            Persona persona = usuario.getPersona();
            usuarioConsulta.nombreCompleto = persona.getNombres()+" "+persona.getApellidoPaterno()+" "+persona.getApellidoMaterno();
            usuarioConsulta.perfil = usuario.getTipoUsuario().getTipo();
            usuarioConsulta.fechaRegistro = sdf.format(usuario.getFechaRegistro());
            usuarioConsulta.estado = usuario.getEstadoUsuario().getId();
            usuariosConsulta.add(usuarioConsulta);
        }

        if(entrada.nombre!=null && !entrada.nombre.isEmpty()){
            List<UsuarioConsulta> usuariosFiltrados = usuariosConsulta.stream()
                    .filter(usuarioConsulta -> usuarioConsulta.nombreUsuario.toLowerCase().contains(entrada.nombre.toLowerCase()))
                    .collect(Collectors.toList());
            if(usuariosFiltrados.isEmpty()){
                salida.setEstado(HttpStatus.NOT_FOUND);
                salida.setMensaje("No se encontaron usuarios");
                salida.setErrores(+1);
                return  salida;
            }
            salida.setUsuarios(usuariosFiltrados);

        }else{
            salida.setUsuarios(usuariosConsulta);
        }

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Usuarios obtenidos correctamente");

        return salida;
    }
}
