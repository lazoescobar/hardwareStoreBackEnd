package com.hardwareStore.backEnd.moduloUsuarios.api;


import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.LoginUsuario.*;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.LoginUsuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("modulo-usuario/")
public class Usuario {

    private final LoginUsuario loginUsuario;
    public Usuario(LoginUsuario loginUsuario){
        this.loginUsuario = loginUsuario;
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody EntradaLoginUsuario entradaLoginUsuario){
        SalidaLoginUsuario salidaLoginUsuario = loginUsuario.Logica(entradaLoginUsuario);
        return ResponseEntity.status(salidaLoginUsuario.getEstado()).body(salidaLoginUsuario);
    }


    @PostMapping("registrar")
    public ResponseEntity<Object> registrar(){

        return ResponseEntity.status(400).body(null);
    }
}
