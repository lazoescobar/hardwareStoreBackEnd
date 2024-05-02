package com.hardwareStore.backEnd.moduloInventario.servicios.tipoProductos;

import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.TipoMovimientoProducto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.TipoMovimientoProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ObtenerTiposMovimientos {

    private final TipoMovimientoProductoRepository tipoMovimientoProductoRepository;

    public ObtenerTiposMovimientos(TipoMovimientoProductoRepository tipoMovimientoProductoRepository){
        this.tipoMovimientoProductoRepository = tipoMovimientoProductoRepository;
    }

    public class SalidaObtenerTipoMovimientos extends SalidaBaseService {
        HashMap <String, TipoMovimientoProducto>  tipoMovimientos;

        public void addNuevoMovimiento(String tipoMovimiento, TipoMovimientoProducto tipoMovimientoProducto){
            if(tipoMovimientos == null){
                tipoMovimientos = new HashMap<>();
            }
            tipoMovimientos.put(tipoMovimiento, tipoMovimientoProducto);
        }

        public HashMap<String, TipoMovimientoProducto> getTipoMovimientos() {
            return tipoMovimientos;
        }

        public void setTipoMovimientos(HashMap<String, TipoMovimientoProducto> tipoMovimientos) {
            this.tipoMovimientos = tipoMovimientos;
        }
    }

    public SalidaObtenerTipoMovimientos logica(){
        SalidaObtenerTipoMovimientos salida = new SalidaObtenerTipoMovimientos();

        List<TipoMovimientoProducto> movimientosProducto = tipoMovimientoProductoRepository.findAll();
        if(movimientosProducto.isEmpty()){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("No existen tipos de movimientos para productos");
            salida.setErrores(+1);
        }

        for(TipoMovimientoProducto tipoMovimientoProducto: movimientosProducto){
            salida.addNuevoMovimiento(tipoMovimientoProducto.getId(), tipoMovimientoProducto);
        }
        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Movimientos producto encontrados correctamente");
        return salida;
    }

}
