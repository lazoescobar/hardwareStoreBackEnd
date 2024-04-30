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


    public class SalidaBuscarProductoPorId extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Producto producto;

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
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

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Producto encontrado correctamente");
        salida.setProducto(producto);

        return salida;
    }
}
