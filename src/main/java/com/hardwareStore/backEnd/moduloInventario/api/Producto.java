package com.hardwareStore.backEnd.moduloInventario.api;

import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.BuscarProductoPorId.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.CambiarNombreProducto.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.ConsultarProductos.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.ConsultarStockActualPorId.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.ConsultaProductoPorNombre;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.ConsultaProductoPorNombre.*;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.RegistrarNuevoProducto;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.RegistrarNuevoProducto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("modulo-inventario/producto")
public class Producto {

    private final BuscarProductoPorId buscarProductoPorId;
    private final CambiarNombreProducto cambiarNombreProducto;
    private final ConsultarStockActualPorId consultarStockActualPorId;
    private final ConsultarProductos consultarProductos;
    private final ConsultaProductoPorNombre consultaProductoPorNombre;
    private final RegistrarNuevoProducto registrarNuevoProducto;

    public Producto(BuscarProductoPorId buscarProductoPorId,
                    CambiarNombreProducto cambiarNombreProducto,
                    ConsultarStockActualPorId consultarStockActualPorId,
                    ConsultarProductos consultarProductos,
                    ConsultaProductoPorNombre consultaProductoPorNombre,
                    RegistrarNuevoProducto registrarNuevoProducto){
        this.buscarProductoPorId = buscarProductoPorId;
        this.cambiarNombreProducto = cambiarNombreProducto;
        this.consultarStockActualPorId = consultarStockActualPorId;
        this.consultarProductos = consultarProductos;
        this.consultaProductoPorNombre = consultaProductoPorNombre;
        this.registrarNuevoProducto = registrarNuevoProducto;
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

    @PostMapping("/consulta-productos")
    public ResponseEntity<SalidaConsultarProductos> consultaProductos(@RequestBody EntradaConsultarProductos entradaConsultarProductos){
        SalidaConsultarProductos salida = consultarProductos.Logica(entradaConsultarProductos);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }

    @PostMapping("/consulta-productos-por-nombre")
    public ResponseEntity<SalidaConsultaProductoPorNombre> consultaProductosPorNombre(@RequestBody EntradaConsultaProductoPorNombre entradaConsultaProductoPorNombre){
        SalidaConsultaProductoPorNombre salida = consultaProductoPorNombre.logica(entradaConsultaProductoPorNombre);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }

    @PostMapping("/nuevo-producto")
    public ResponseEntity<SalidaBaseService> registrarNuevoProducto(@RequestBody EntradaRegistrarNuevoProducto entradaRegistrarNuevoProducto){
        SalidaBaseService salida = registrarNuevoProducto.logica(entradaRegistrarNuevoProducto);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }


}
