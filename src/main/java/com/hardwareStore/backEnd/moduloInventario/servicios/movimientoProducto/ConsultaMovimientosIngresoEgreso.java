package com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConsultaMovimientosIngresoEgreso {

    public static final String INGRESO = "ING";
    public static final String EGRESO = "EGR";

    @Autowired
    private final MovimientoProductoRepository movimientoProductoRepository;

    @Autowired
    BuscarProductoPorId buscarProductoPorId;

    public ConsultaMovimientosIngresoEgreso(MovimientoProductoRepository movimientoProductoRepository){
        this.movimientoProductoRepository = movimientoProductoRepository;
    }

    public class Movimiento{
        @JsonIgnore
        public Integer id;
        public String fechaMovimiento;
        public String tipoMovimiento;

        public Integer ingreso;

        public Integer egreso;

        public Integer stock;

        public Integer getId() {
            return id;
        }
    }


    public class SalidaConsultaMovimientosIngresoEgreso extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public List<Movimiento> movimientos;

        public void setMovimientos(List<Movimiento> movimientos) {
            this.movimientos = movimientos;
        }

        public List<Movimiento> getMovimientos() {
            return movimientos;
        }
    }


    public SalidaConsultaMovimientosIngresoEgreso Logica(Integer idProducto){
        SalidaConsultaMovimientosIngresoEgreso salida = new SalidaConsultaMovimientosIngresoEgreso();

        BuscarProductoPorId.SalidaBuscarProductoPorId servicioBuscarProductoPorId = buscarProductoPorId.Logica(idProducto);
        if(servicioBuscarProductoPorId.getErrores() > 0){
            salida.setEstado(servicioBuscarProductoPorId.getEstado());
            salida.setMensaje(servicioBuscarProductoPorId.getMensaje());
            salida.setErrores(servicioBuscarProductoPorId.getErrores());
            return salida;
        }

        BuscarProductoPorId.InfoProducto infoProducto = servicioBuscarProductoPorId.getInfoProducto();
        Producto producto = new Producto();
        producto.setId(infoProducto.getId());

        List<MovimientoProducto> movimientosProducto = movimientoProductoRepository.findByProducto(producto);
        if(movimientosProducto.isEmpty()){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje("Sin registro de movimientos");
            salida.setErrores(+1);
            return salida;
        }

        List<Movimiento> movimientos = new ArrayList<>();
        for(MovimientoProducto movimientoProducto: movimientosProducto){
            Movimiento movimiento = new Movimiento();
            movimiento.id = movimientoProducto.getId();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            movimiento.fechaMovimiento = sdf.format(movimientoProducto.getFechaRegistro());
            movimiento.tipoMovimiento = movimientoProducto.getTipoMovimientoProducto().getTipo();
            if(Objects.equals(movimientoProducto.getTipoMovimientoProducto().getId(), INGRESO)){
                movimiento.ingreso = movimientoProducto.getCantidad();
            }
            else{
                movimiento.egreso = movimientoProducto.getCantidad();
            }
            movimiento.stock = movimientoProducto.getCantidadPosterior();
            movimientos.add(movimiento);
        }


        List<Movimiento> movimientosOrdenados = movimientos.stream()
                                                            .sorted(Comparator.comparingInt(Movimiento::getId).reversed())
                                                            .collect(Collectors.toList());

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Movimiento encontrados correctamente");
        salida.setMovimientos(movimientosOrdenados);

        return salida;
    }
}
