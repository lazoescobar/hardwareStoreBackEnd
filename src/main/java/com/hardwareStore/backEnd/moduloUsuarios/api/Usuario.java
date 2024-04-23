package com.hardwareStore.backEnd.moduloUsuarios.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("modulo-usuario/")
public class Usuario {


    @PostMapping("registrar")
    public ResponseEntity<Object> registrar(){

        return ResponseEntity.status(400).body(null);
    }
}
