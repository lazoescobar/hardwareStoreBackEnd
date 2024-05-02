package com.hardwareStore.backEnd.moduloInventario.api;

import com.hardwareStore.backEnd.moduloInventario.servicios.productos.BuscarProductoPorId;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.BuscarProductoPorId.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.CambiarNombreProducto;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.CambiarNombreProducto.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.ConsultarStockActualPorId;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.ConsultarStockActualPorId.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto.RegistrarNuevoIngresoEgresoProducto;
import com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto.RegistrarNuevoIngresoEgresoProducto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("modulo-inventario/producto")
public class Producto {

    private final BuscarProductoPorId buscarProductoPorId;
    private final CambiarNombreProducto cambiarNombreProducto;
    private final ConsultarStockActualPorId consultarStockActualPorId;
    private final RegistrarNuevoIngresoEgresoProducto registrarNuevoIngresoEgresoProducto;

    public Producto(BuscarProductoPorId buscarProductoPorId,
                    CambiarNombreProducto cambiarNombreProducto,
                    ConsultarStockActualPorId consultarStockActualPorId,
                    RegistrarNuevoIngresoEgresoProducto registrarNuevoIngresoEgresoProducto){
        this.buscarProductoPorId = buscarProductoPorId;
        this.cambiarNombreProducto = cambiarNombreProducto;
        this.consultarStockActualPorId = consultarStockActualPorId;
        this.registrarNuevoIngresoEgresoProducto = registrarNuevoIngresoEgresoProducto;
    }

    @GetMapping("/informacion/{id}")
    public ResponseEntity<SalidaBuscarProductoPorId> obtenerInformacion(@PathVariable Integer id){
        SalidaBuscarProductoPorId salida = buscarProductoPorId.Logica(id);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }


    @PostMapping("/cambio-nombre")
    public ResponseEntity<SalidaambiarNombreProducto> cambiarNombre(@RequestBody EntradaCambiarNombreProducto entradaCambiarNombreProducto){
        SalidaambiarNombreProducto salida = cambiarNombreProducto.logica(entradaCambiarNombreProducto);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }

    @GetMapping("/stock-actual/{id}")
    public ResponseEntity<SalidaConsultarStockActualPorId> obtenerStockActual(@PathVariable Integer id){
        SalidaConsultarStockActualPorId salida = consultarStockActualPorId.Logica(id);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }

    @PostMapping("/registrar-movimiento-producto/{id}")
    public ResponseEntity<SalidaRegistrarNuevoIngresoEgresoProducto> actualizarStock( @PathVariable Integer id, @RequestBody EntadaRegistrarNuevoIngresoEgresoProducto entadaRegistrarNuevoIngresoEgresoProducto){
        SalidaRegistrarNuevoIngresoEgresoProducto salida = registrarNuevoIngresoEgresoProducto.Logica(id, entadaRegistrarNuevoIngresoEgresoProducto);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }


}
