package com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios;

import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloPersona.persistencias.modelos.DocumentoIndentidad;
import com.hardwareStore.backEnd.moduloPersona.persistencias.modelos.EstadoDocumento;
import com.hardwareStore.backEnd.moduloPersona.persistencias.modelos.Persona;
import com.hardwareStore.backEnd.moduloPersona.persistencias.modelos.TipoDocumento;
import com.hardwareStore.backEnd.moduloPersona.persistencias.repositorios.DocumentoIdentidadRepository;
import com.hardwareStore.backEnd.moduloPersona.persistencias.repositorios.EstadoDocumentoRepository;
import com.hardwareStore.backEnd.moduloPersona.persistencias.repositorios.PersonaRepository;
import com.hardwareStore.backEnd.moduloPersona.persistencias.repositorios.TipoDocumentoRepository;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.EstadoUsuario;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.TipoUsuario;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.EstadoUsuarioRepository;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.TipoUsuarioRepository;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RegistrarNuevoUsuario {

    public static final String DUE = "DUE";
    public static final String NEG = "NEG";

    public static final String P = "P";
    public static final String R = "R";

    public final DocumentoIdentidadRepository documentoIdentidadRepository;
    public final UsuarioRepository usuarioRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final EstadoDocumentoRepository estadoDocumentoRepository;
    private final EstadoUsuarioRepository estadoUsuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final PersonaRepository personaRepository;
    @Autowired
    private BuscarUsuarioPorId buscarUsuarioPorId;

    public RegistrarNuevoUsuario(DocumentoIdentidadRepository documentoIdentidadRepository, UsuarioRepository usuarioRepository,
                                 TipoDocumentoRepository tipoDocumentoRepository, EstadoDocumentoRepository estadoDocumentoRepository,
                                 EstadoUsuarioRepository estadoUsuarioRepository, TipoUsuarioRepository tipoUsuarioRepository,
                                 PersonaRepository personaRepository) {
        this.documentoIdentidadRepository = documentoIdentidadRepository;
        this.usuarioRepository = usuarioRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.estadoDocumentoRepository = estadoDocumentoRepository;
        this.estadoUsuarioRepository = estadoUsuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.personaRepository = personaRepository;
    }

    public static class EntradaRegistrarNuevoUsuario {
        public Integer idUsuario;
        public String tipoUsuario;
        public String tipoDocumento;
        public String numeroDocumento;
        public String nombres;
        public String apellidoPaterno;
        public String apellidoMaterno;
        public String nombreUsuario;
        public String password;
    }


    public SalidaBaseService logica(EntradaRegistrarNuevoUsuario entrada){
        SalidaBaseService salida = new SalidaBaseService();

        BuscarUsuarioPorId.SalidaBuscarUsuarioPorId servicioaBuscarUsuarioPorId = buscarUsuarioPorId.logica(entrada.idUsuario);
        if(servicioaBuscarUsuarioPorId.getErrores() > 0){
            salida.setEstado(servicioaBuscarUsuarioPorId.getEstado());
            salida.setMensaje(servicioaBuscarUsuarioPorId.getMensaje());
            salida.setErrores(servicioaBuscarUsuarioPorId.getErrores());
            return salida;
        }

        if(entrada.tipoUsuario == null || entrada.tipoUsuario.isEmpty() && (!entrada.tipoUsuario.equals(DUE) && !entrada.tipoUsuario.equals(NEG))){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Tipo de usuario no valido");
            salida.setErrores(+1);
            return salida;
        }

        if(entrada.tipoDocumento == null || entrada.tipoDocumento.isEmpty() && (!entrada.tipoDocumento.equals(P) && !entrada.tipoDocumento.equals(R))){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Tipo de documento no valido");
            salida.setErrores(+1);
            return salida;
        }

        String documentoConsultar = "";
        if(!entrada.numeroDocumento.contains("-")) {
            String numerDocu = entrada.numeroDocumento;
            int largoNumeroDocu = numerDocu.length();
            String doc = numerDocu.substring(0, largoNumeroDocu - 1);
            String dv = numerDocu.substring(largoNumeroDocu - 1, largoNumeroDocu);
            documentoConsultar = doc+"-"+dv;
        }
        else{
            documentoConsultar = entrada.numeroDocumento;
        }
        DocumentoIndentidad documentoIndentidad = documentoIdentidadRepository.findByNumero(documentoConsultar);
        if(documentoIndentidad != null){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Ya existe documento " + entrada.numeroDocumento+" de identidad registrado");
            salida.setErrores(+1);
            return salida;
        }

        String nombreUsuario = entrada.nombreUsuario.toLowerCase();
        Usuario usuario = usuarioRepository.findFirstByNombre(nombreUsuario);
        if(usuario !=  null){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Ya existe nombre de usuario" + nombreUsuario + " registrado");
            salida.setErrores(+1);
            return salida;
        }

        TipoDocumento tipoDocumento = tipoDocumentoRepository.findFirstById(entrada.tipoDocumento);
        EstadoDocumento  estadoDocumento = estadoDocumentoRepository.findFirstById("ACT");
        EstadoUsuario  estadoUsuario = estadoUsuarioRepository.findFirstById("ACT");
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findFirsttById(entrada.tipoUsuario);

        this.transascion(salida, entrada, tipoDocumento, estadoDocumento, tipoUsuario, estadoUsuario);

        return salida;
    }

    public void transascion(SalidaBaseService salidaBaseService, EntradaRegistrarNuevoUsuario entradaRegistrarNuevoUsuario, TipoDocumento tipoDocumento, EstadoDocumento estadoDocumento, TipoUsuario tipoUsuario, EstadoUsuario estadoUsuario){

        try{
            Persona persona = new Persona(entradaRegistrarNuevoUsuario.nombres, entradaRegistrarNuevoUsuario.apellidoPaterno, entradaRegistrarNuevoUsuario.apellidoMaterno, new Timestamp(System.currentTimeMillis()));
            Persona personaRegistada = personaRepository.saveAndFlush(persona);

            DocumentoIndentidad documentoIndentidad = new DocumentoIndentidad(entradaRegistrarNuevoUsuario.numeroDocumento, personaRegistada, tipoDocumento, estadoDocumento);
            DocumentoIndentidad documentoIndentidadRegistrado = documentoIdentidadRepository.saveAndFlush(documentoIndentidad);

            Usuario usuario = new Usuario(entradaRegistrarNuevoUsuario.nombreUsuario, entradaRegistrarNuevoUsuario.password, tipoUsuario, persona, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), estadoUsuario);
            Usuario usuarioRegistrado = usuarioRepository.saveAndFlush(usuario);
            salidaBaseService.setEstado(HttpStatus.OK);
            salidaBaseService.setMensaje("Usuario " + entradaRegistrarNuevoUsuario.nombreUsuario + " registrado correctemnte");
        }catch (Exception e){
            salidaBaseService.setEstado(HttpStatus.BAD_REQUEST);
            salidaBaseService.setMensaje("Problemas al registrar nuevo usuario");
            salidaBaseService.setErrores(+1);
        }
    }






}
