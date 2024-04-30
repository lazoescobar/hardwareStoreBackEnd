package com.hardwareStore.backEnd.moduloInventario.api;

import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.BuscarProductoPorId;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.BuscarProductoPorId.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.CambiarNombreProducto;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.CambiarNombreProducto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("modulo-inventario/producto")
public class Producto {

    private final BuscarProductoPorId buscarProductoPorId;

    private final CambiarNombreProducto cambiarNombreProducto;

    public Producto(BuscarProductoPorId buscarProductoPorId, CambiarNombreProducto cambiarNombreProducto){
        this.buscarProductoPorId = buscarProductoPorId;
        this.cambiarNombreProducto = cambiarNombreProducto;
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


    @PostMapping("/cambio-nombre")
    public ResponseEntity<SalidaBaseService> cambiarNombre(@RequestBody EntradaCambiarNombreProducto entradaCambiarNombreProducto){
        SalidaBaseService salida = cambiarNombreProducto.logica(entradaCambiarNombreProducto);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }

}
