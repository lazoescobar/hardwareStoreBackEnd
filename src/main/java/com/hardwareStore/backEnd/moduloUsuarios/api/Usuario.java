package com.hardwareStore.backEnd.moduloUsuarios.api;

import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.ConsultaUsuarios;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.ConsultaUsuarios.*;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.LoginUsuario.*;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.LoginUsuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("modulo-usuario/usuario")
public class Usuario {

    private final LoginUsuario loginUsuario;
    private final ConsultaUsuarios consultaUsuarios;
    public Usuario(LoginUsuario loginUsuario,
                   ConsultaUsuarios consultaUsuarios){
        this.loginUsuario = loginUsuario;
        this.consultaUsuarios = consultaUsuarios;
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
}
