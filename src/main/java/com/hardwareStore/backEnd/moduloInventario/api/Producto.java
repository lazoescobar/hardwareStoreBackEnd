package com.hardwareStore.backEnd.moduloInventario.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("modulo-inventario/producto")
public class Producto {

    @PostMapping("registrar")
    public ResponseEntity<Object> registrar(){

        return ResponseEntity.status(400).body(null);
    }
}
