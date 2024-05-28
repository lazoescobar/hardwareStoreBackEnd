package com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.VisualizaEnFront;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LoginUsuario {

    private final UsuarioRepository usuarioRepository;

    public LoginUsuario(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public static class EntradaLoginUsuario {
        public String uss;
        public String pass;
    }

    public class InfoAcceso {
        public String nombre;
        public String ruta;
    }


    public class SalidaLoginUsuario extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public Integer id;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String tipo;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String nombreUsuario;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<List<InfoAcceso>> accesos;
        public void agregarAcceso(List<InfoAcceso> listaAccesos){
            if(accesos == null){
                accesos = new ArrayList<>();
            }
            accesos.add(listaAccesos);
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public void setAccesos(List<List<InfoAcceso>> accesos) {
            this.accesos = accesos;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public List<List<InfoAcceso>> getAccesos() {
            return accesos;
        }
    }


    public SalidaLoginUsuario logica(EntradaLoginUsuario entradaLogin){

        SalidaLoginUsuario salida = new SalidaLoginUsuario();
        String nombre = entradaLogin.uss;
        String pass = entradaLogin.pass;

        String mensajeError = "usuario o pass no validos";
        if((nombre == null || nombre.isEmpty()) && (pass == null || !pass.isEmpty())){
            salida.setEstado(HttpStatus.UNAUTHORIZED);
            salida.setMensaje(mensajeError);
            return salida;
        }

        Usuario usuarioEntidad = usuarioRepository.findFirstByNombreAndPass(nombre, pass);
        if(usuarioEntidad == null){
            salida.setEstado(HttpStatus.UNAUTHORIZED);
            salida.setMensaje(mensajeError);
            return salida;
        }

        if(Objects.equals(usuarioEntidad.getEstadoUsuario().getId(), "BLO")){
            salida.setEstado(HttpStatus.UNAUTHORIZED);
            salida.setMensaje("usuario con estado BLOQUEADO");
            return salida;
        }

        if(usuarioEntidad.getTipoUsuario().getVisualizacionesEnFront().isEmpty()){
            salida.setEstado(HttpStatus.UNAUTHORIZED);
            salida.setMensaje("usuario sin PERMISOS");
            return salida;
        }

        salida.setId(usuarioEntidad.getId());
        salida.setNombreUsuario(usuarioEntidad.getNombre());
        salida.setTipo(usuarioEntidad.getTipoUsuario().getId());

        List<VisualizaEnFront> visualizacionesEnFront = usuarioEntidad.getTipoUsuario().getVisualizacionesEnFront();

        List<InfoAcceso> accesos = new ArrayList<>();
        int cantidadVisualizaciones = visualizacionesEnFront.size();
        int contador = 1;
        for(VisualizaEnFront visualizaEnFront : visualizacionesEnFront){
            InfoAcceso infoAcceso = new InfoAcceso();
            infoAcceso.nombre = visualizaEnFront.getVisualiza();
            infoAcceso.ruta = visualizaEnFront.getUrlVisualiza();
            accesos.add(infoAcceso);
            if(accesos.size() < 2 && contador == cantidadVisualizaciones){
                salida.agregarAcceso(accesos);
            }
            if(accesos.size() == 2){
                salida.agregarAcceso(accesos);
                accesos = new ArrayList<>();
            }
            contador ++;
        }

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Usuario autenticado correctamente");
        return salida;
    }
}
