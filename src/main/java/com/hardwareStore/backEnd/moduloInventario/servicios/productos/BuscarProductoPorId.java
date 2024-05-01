package com.hardwareStore.backEnd.moduloInventario.servicios.productos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.Producto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BuscarProductoPorId {

    @Autowired
    private final ProductoRepository productoRepository;

    @Autowired
    public BuscarProductoPorId(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }


    public class InfoProducto extends Producto{
        private String tipo;
        private String descTipo;

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getDescTipo() {
            return descTipo;
        }

        public void setDescTipo(String descTipo) {
            this.descTipo = descTipo;
        }
    }


    public class SalidaBuscarProductoPorId extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        InfoProducto infoProducto;

        public InfoProducto getInfoProducto() {
            return infoProducto;
        }

        public void setInfoProducto(InfoProducto infoProducto) {
            this.infoProducto = infoProducto;
        }
    }


    public SalidaBuscarProductoPorId Logica(Integer id){

        SalidaBuscarProductoPorId salida = new SalidaBuscarProductoPorId();

        String mensajeError = "producto no encontrado";
        if(id == null || id == 0){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje(mensajeError);
            salida.setErrores(+1);
            return salida;
        }

        Producto producto = productoRepository.findFirstById(id);
        if(producto == null){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje(mensajeError);
            salida.setErrores(+1);
            return salida;
        }

        InfoProducto infoProducto = new InfoProducto();
        infoProducto.setId(producto.getId());
        infoProducto.setNombre(producto.getNombre());
        infoProducto.setFechaRegistro(producto.getFechaRegistro());
        infoProducto.setFechaModificacion(producto.getFechaModificacion());
        infoProducto.setTipo(producto.getTipoProducto().getId());
        infoProducto.setDescTipo(producto.getTipoProducto().getTipo());

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Producto encontrado correctamente");
        salida.setInfoProducto(infoProducto);

        return salida;
    }
}
