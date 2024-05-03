package com.hardwareStore.backEnd.moduloInventario.api;

import com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto.RegistrarNuevoIngresoEgresoProducto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("modulo-inventario/movimiento-producto")
public class MovimientoProducto {

    private final RegistrarNuevoIngresoEgresoProducto registrarNuevoIngresoEgresoProducto;

    public MovimientoProducto(RegistrarNuevoIngresoEgresoProducto registrarNuevoIngresoEgresoProducto){
        this.registrarNuevoIngresoEgresoProducto = registrarNuevoIngresoEgresoProducto;
    }

    @PostMapping("/registrar-nuevo-movimiento/{idProducto}")
    public ResponseEntity<RegistrarNuevoIngresoEgresoProducto.SalidaRegistrarNuevoIngresoEgresoProducto> nuevoIngreso(@PathVariable Integer idProducto, @RequestBody RegistrarNuevoIngresoEgresoProducto.EntadaRegistrarNuevoIngresoEgresoProducto entadaRegistrarNuevoIngresoEgresoProducto){
        RegistrarNuevoIngresoEgresoProducto.SalidaRegistrarNuevoIngresoEgresoProducto salida = registrarNuevoIngresoEgresoProducto.Logica(idProducto, entadaRegistrarNuevoIngresoEgresoProducto);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }
}
