package com.hardwareStore.backEnd.moduloInventario.servicios.productos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.MovimientoProducto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.MovimientoProductoRepository;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ConsultarStockActualPorId {

    private final ProductoRepository productoRepository;
    @Autowired
    private final MovimientoProductoRepository movimientoProductoRepository;

    @Autowired
    BuscarProductoPorId buscarProductoPorId;

    @Autowired
    public ConsultarStockActualPorId(ProductoRepository productoRepository, MovimientoProductoRepository movimientoProductoRepository){
        this.productoRepository = productoRepository;
        this.movimientoProductoRepository = movimientoProductoRepository;
    }


    public class SalidaConsultarStockActualPorId extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer stockActual;

        public Integer getStockActual() {
            return stockActual;
        }

        public void setStockActual(Integer stockActual) {
            this.stockActual = stockActual;
        }
    }


    public SalidaConsultarStockActualPorId Logica(Integer id){

        SalidaConsultarStockActualPorId salida = new SalidaConsultarStockActualPorId();

        BuscarProductoPorId.SalidaBuscarProductoPorId servicioBuscarProductoPorId = buscarProductoPorId.Logica(id);
        if(servicioBuscarProductoPorId.getErrores() > 0){
            salida.setEstado(servicioBuscarProductoPorId.getEstado());
            salida.setMensaje(servicioBuscarProductoPorId.getMensaje());
            salida.setErrores(servicioBuscarProductoPorId.getErrores());
            return salida;
        }

        MovimientoProducto movimientoProducto = movimientoProductoRepository.findTopByProductoId(id);
        if(movimientoProducto == null){
            salida.stockActual = 0;
        }
        else{
            salida.stockActual = movimientoProducto.getCantidadPosterior();
        }

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Stock producto encontrado correctamente");

        return salida;
    }
}
