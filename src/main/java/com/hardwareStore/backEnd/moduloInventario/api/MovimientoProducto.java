package com.hardwareStore.backEnd.moduloInventario.api;

import com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto.ConsultaMovimientosIngresoEgreso;
import com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto.ConsultaMovimientosIngresoEgreso.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto.ConsultaMovimientosPorRangoFechas;
import com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto.ConsultaMovimientosPorRangoFechas.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto.RegistrarNuevoIngresoEgresoProducto;
import com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto.RegistrarNuevoIngresoEgresoProducto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("modulo-inventario/movimiento-producto")
public class MovimientoProducto {

    private final RegistrarNuevoIngresoEgresoProducto registrarNuevoIngresoEgresoProducto;
    private final ConsultaMovimientosIngresoEgreso consultaMovimientosIngresoEgreso;
    private final ConsultaMovimientosPorRangoFechas consultaMovimientosPorRangoFechas;

    public MovimientoProducto(RegistrarNuevoIngresoEgresoProducto registrarNuevoIngresoEgresoProducto,
                              ConsultaMovimientosIngresoEgreso consultaMovimientosIngresoEgreso,
                              ConsultaMovimientosPorRangoFechas consultaMovimientosPorRangoFechas){
        this.registrarNuevoIngresoEgresoProducto = registrarNuevoIngresoEgresoProducto;
        this.consultaMovimientosIngresoEgreso = consultaMovimientosIngresoEgreso;
        this.consultaMovimientosPorRangoFechas = consultaMovimientosPorRangoFechas;
    }

    @PostMapping("/registrar-nuevo-movimiento/{idProducto}")
    public ResponseEntity<SalidaRegistrarNuevoIngresoEgresoProducto> nuevoIngreso(@PathVariable Integer idProducto, @RequestBody EntadaRegistrarNuevoIngresoEgresoProducto entadaRegistrarNuevoIngresoEgresoProducto){
        SalidaRegistrarNuevoIngresoEgresoProducto salida = registrarNuevoIngresoEgresoProducto.logica(idProducto, entadaRegistrarNuevoIngresoEgresoProducto);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }

    @GetMapping("/consulta-movimientos/{idProducto}")
    public ResponseEntity<SalidaConsultaMovimientosIngresoEgreso> consultaMovimientos(@PathVariable Integer idProducto){
        SalidaConsultaMovimientosIngresoEgreso salida = consultaMovimientosIngresoEgreso.Logica(idProducto);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }

    @PostMapping("/consulta-movimientos-por-rango-fechas")
    public ResponseEntity<SalidaConsultaMovimientosPorRangoFechas> consultaMovimientosPorRangoFechas(@RequestBody EntradaConsultaMovimientosPorRangoFechas entradaConsultaMovimientosPorRangoFechas){
        SalidaConsultaMovimientosPorRangoFechas salida = consultaMovimientosPorRangoFechas.logica(entradaConsultaMovimientosPorRangoFechas);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }
}
