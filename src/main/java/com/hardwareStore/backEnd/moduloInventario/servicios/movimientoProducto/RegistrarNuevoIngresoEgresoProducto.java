package com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.MovimientoProducto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.Producto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.TipoMovimientoProducto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.MovimientoProductoRepository;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.BuscarProductoPorId;
import com.hardwareStore.backEnd.moduloInventario.servicios.productos.ConsultarStockActualPorId;
import com.hardwareStore.backEnd.moduloInventario.servicios.tipoProductos.ObtenerTiposMovimientos;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.BuscarUsuarioPorId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Objects;

@Service
public class RegistrarNuevoIngresoEgresoProducto {

    public static final String INGRESO = "ING";
    public static final String EGRESO = "EGR";

    @Autowired
    private final MovimientoProductoRepository movimientoProductoRepository;
    @Autowired
    BuscarUsuarioPorId buscarUsuarioPorId;
    @Autowired
    ConsultarStockActualPorId consultarStockActualPorId;
    @Autowired
    ObtenerTiposMovimientos obtenerTiposMovimientos;
    @Autowired
    BuscarProductoPorId buscarProductoPorId;

    public RegistrarNuevoIngresoEgresoProducto(MovimientoProductoRepository movimientoProductoRepository){
        this.movimientoProductoRepository = movimientoProductoRepository;
    }


    public static class EntadaRegistrarNuevoIngresoEgresoProducto{

        public Integer idUsuario;
        public String tipoMovimiento;
        public Integer cantidad;
    }

    public class SalidaRegistrarNuevoIngresoEgresoProducto extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer stockActual;

        public Integer getStockActual() {
            return stockActual;
        }

        public void setStockActual(Integer stockActual) {
            this.stockActual = stockActual;
        }
    }


    public SalidaRegistrarNuevoIngresoEgresoProducto Logica(Integer idProducto, EntadaRegistrarNuevoIngresoEgresoProducto entrada){

        SalidaRegistrarNuevoIngresoEgresoProducto salida = new SalidaRegistrarNuevoIngresoEgresoProducto();

        BuscarUsuarioPorId.SalidaBuscarUsuarioPorId servicioaBuscarUsuarioPorId = buscarUsuarioPorId.logica(entrada.idUsuario);
        if(servicioaBuscarUsuarioPorId.getErrores() > 0){
            salida.setEstado(servicioaBuscarUsuarioPorId.getEstado());
            salida.setMensaje(servicioaBuscarUsuarioPorId.getMensaje());
            salida.setErrores(servicioaBuscarUsuarioPorId.getErrores());
            return salida;
        }
        Usuario usuario = servicioaBuscarUsuarioPorId.getUsuario();

        ConsultarStockActualPorId.SalidaConsultarStockActualPorId servicioConsultarStockActualPorId = consultarStockActualPorId.Logica(idProducto);
        if(servicioConsultarStockActualPorId.getErrores() > 0){
            salida.setEstado(servicioConsultarStockActualPorId.getEstado());
            salida.setMensaje(servicioConsultarStockActualPorId.getMensaje());
            salida.setErrores(servicioConsultarStockActualPorId.getErrores());
            return salida;
        }

        if(entrada.tipoMovimiento == null){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Tipo de movimiento no permitido");
            salida.setErrores(+1);
            return salida;
        }
        ObtenerTiposMovimientos.SalidaObtenerTipoMovimientos servicioObtenerTipoMovimientos = obtenerTiposMovimientos.logica();
        HashMap<String, TipoMovimientoProducto> hashTipoMovimientos = servicioObtenerTipoMovimientos.getTipoMovimientos();
        TipoMovimientoProducto tipoMovimientoProducto = hashTipoMovimientos.get(entrada.tipoMovimiento);
        if(tipoMovimientoProducto == null){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Tipo de movimiento no permitido");
            salida.setErrores(+1);
            return salida;
        }

        Integer stockActual = servicioConsultarStockActualPorId.getStockActual();
        if(entrada.cantidad == 0){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Cantiad debe ser mayor a 0");
            salida.setErrores(+1);
            return salida;
        }

        BuscarProductoPorId.SalidaBuscarProductoPorId servicioBuscarProductoPorId = buscarProductoPorId.Logica(idProducto);
        BuscarProductoPorId.InfoProducto infoProducto = servicioBuscarProductoPorId.getInfoProducto();
        Producto producto = new Producto();
        producto.setId(infoProducto.getId());

        //LOGICA EGRESO
        if(Objects.equals(tipoMovimientoProducto.getId(), EGRESO)){
            if(stockActual == 0 || stockActual < entrada.cantidad){
                salida.setEstado(HttpStatus.BAD_REQUEST);
                salida.setMensaje("Stock es menor a la cantidad ingresada en egreso");
                salida.setErrores(+1);
                return salida;
            }

            transaccion(salida, entrada, EGRESO, producto, tipoMovimientoProducto, usuario, stockActual);
        }

        //INGRESO
        if(Objects.equals(tipoMovimientoProducto.getId(), INGRESO)){
            transaccion(salida, entrada, INGRESO, producto, tipoMovimientoProducto, usuario, stockActual);
        }

        return salida;
    }


    public void transaccion(SalidaRegistrarNuevoIngresoEgresoProducto salida, EntadaRegistrarNuevoIngresoEgresoProducto entrada,
                            String tipoMovimiento, Producto producto, TipoMovimientoProducto tipoMovimientoProducto, Usuario usuario, Integer stockActual){

        try{
            Integer nuevoStock = (Objects.equals(tipoMovimiento, EGRESO)) ? stockActual - entrada.cantidad : stockActual + entrada.cantidad;
            MovimientoProducto movimientoProductoRegistrar = new MovimientoProducto();
            movimientoProductoRegistrar.setCantidad(entrada.cantidad);
            movimientoProductoRegistrar.setCantidadPrevia(stockActual);
            movimientoProductoRegistrar.setCantidadPosterior(nuevoStock);
            movimientoProductoRegistrar.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
            movimientoProductoRegistrar.setProducto(producto);
            movimientoProductoRegistrar.setTipoMovimientoProducto(tipoMovimientoProducto);
            movimientoProductoRegistrar.setUsuario(usuario);
            MovimientoProducto movimientoProducto = movimientoProductoRepository.saveAndFlush(movimientoProductoRegistrar);
            salida.setEstado(HttpStatus.OK);
            salida.setMensaje("Movimiento "+ tipoMovimientoProducto.getTipo() +" registrado correctamente con id " + movimientoProducto.getId());
            salida.setStockActual(nuevoStock);
        }catch (Exception e){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Problemas al registrar movimiento " + tipoMovimientoProducto.getTipo());
        }
    }
}
