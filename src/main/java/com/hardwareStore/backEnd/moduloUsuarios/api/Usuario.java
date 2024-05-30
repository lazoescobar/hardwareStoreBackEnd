package com.hardwareStore.backEnd.moduloUsuarios.api;

import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.*;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.BuscarUsuarioPorId.*;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.CambiarPassword.*;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.ConsultaUsuarios.*;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.LoginUsuario.*;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.RegistrarNuevoUsuario;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.RegistrarNuevoUsuario.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("modulo-usuario/usuario")
public class Usuario {

    private final LoginUsuario loginUsuario;
    private final ConsultaUsuarios consultaUsuarios;
    private final BuscarUsuarioPorId buscarUsuarioPorId;
    private final CambiarPassword cambiarPassword;
    private final RegistrarNuevoUsuario registrarNuevoUsuario;
    public Usuario(LoginUsuario loginUsuario,
                   ConsultaUsuarios consultaUsuarios,
                   BuscarUsuarioPorId buscarUsuarioPorId,
                   CambiarPassword cambiarPassword,
                   RegistrarNuevoUsuario registrarNuevoUsuario){
        this.loginUsuario = loginUsuario;
        this.consultaUsuarios = consultaUsuarios;
        this.buscarUsuarioPorId = buscarUsuarioPorId;
        this.cambiarPassword = cambiarPassword;
        this.registrarNuevoUsuario = registrarNuevoUsuario;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody EntradaLoginUsuario entradaLoginUsuario){
        SalidaLoginUsuario salidaLoginUsuario = loginUsuario.logica(entradaLoginUsuario);
        return ResponseEntity.status(salidaLoginUsuario.getEstado()).body(salidaLoginUsuario);
    }

    @PostMapping("/consulta-usuarios")
    public ResponseEntity<SalidaConsultarUsuarios> consultaUsuarios(@RequestBody EntradaConsultarUsuarios entradaConsultarUsuarios){
        SalidaConsultarUsuarios salida = consultaUsuarios.logica(entradaConsultarUsuarios);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }

    @GetMapping("/consulta-usuario/{id}")
    public ResponseEntity<SalidaBuscarUsuarioPorId> consultaUsuarioPorId(@PathVariable Integer id){
        SalidaBuscarUsuarioPorId salida = buscarUsuarioPorId.logica(id);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }

    @PostMapping("/cambiar-password")
    public ResponseEntity<SalidaBaseService> cambiarPassword(@RequestBody EntradaCambiarPassword entradaCambiarPassword){
        SalidaBaseService salida = cambiarPassword.logica(entradaCambiarPassword);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }

    @PostMapping("/registrar")
    public ResponseEntity<SalidaBaseService> registrar(@RequestBody EntradaRegistrarNuevoUsuario entradaRegistrarNuevoUsuario){
        SalidaBaseService salida = registrarNuevoUsuario.logica(entradaRegistrarNuevoUsuario);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }
}
