package com.hardwareStore.backEnd.moduloInventario.api;

import com.hardwareStore.backEnd.moduloInventario.servicios.productos.BuscarProductoPorId;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.BuscarProductoPorId.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("modulo-inventario/producto")
public class Producto {

    private final BuscarProductoPorId buscarProductoPorId;

    public Producto(BuscarProductoPorId buscarProductoPorId){
        this.buscarProductoPorId = buscarProductoPorId;
    }

    @PostMapping("registrar")
    public ResponseEntity<Object> registrar(){

        return ResponseEntity.status(400).body(null);
    }

    @GetMapping("/informacion/{id}")
    public ResponseEntity<SalidaBuscarProductoPorId> obtenerInformacion(@PathVariable Integer id){
        SalidaBuscarProductoPorId salida = buscarProductoPorId.Logica(id);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }


}
