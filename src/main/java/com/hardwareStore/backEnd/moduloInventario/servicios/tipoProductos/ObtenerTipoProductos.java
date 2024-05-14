package com.hardwareStore.backEnd.moduloInventario.servicios.tipoProductos;

import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.TipoMovimientoProducto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.TipoProducto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.TipoProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class ObtenerTipoProductos {

    private final TipoProductoRepository tipoProductoRepository;

    public ObtenerTipoProductos(TipoProductoRepository tipoProductoRepository){
        this.tipoProductoRepository = tipoProductoRepository;
    }

    public class SalidaObtenerTipoProductos extends SalidaBaseService {
        HashMap<String, TipoProducto> tiposProductos;

        public void addNuevoTipoProducto(String tipo, TipoProducto tipoProducto){
            if(tiposProductos == null){
                tiposProductos = new HashMap<>();
            }
            tiposProductos.put(tipo, tipoProducto);
        }

        public HashMap<String, TipoProducto> getTiposProductos() {
            return tiposProductos;
        }

        public void setTiposProductos(HashMap<String, TipoProducto> tiposProductos) {
            this.tiposProductos = tiposProductos;
        }
    }

    public SalidaObtenerTipoProductos logica(){
        SalidaObtenerTipoProductos salida = new SalidaObtenerTipoProductos();

        List<TipoProducto> tipoProductos = tipoProductoRepository.findAll();
        if(tipoProductos.isEmpty()){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("No existen tipos de productos");
            salida.setErrores(+1);
        }

        for(TipoProducto tipoProducto: tipoProductos){
            salida.addNuevoTipoProducto(tipoProducto.getId(), tipoProducto);
        }
        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Tipos de producto encontrados correctamente");
        return salida;
    }
}
