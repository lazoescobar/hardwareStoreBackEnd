package com.hardwareStore.backEnd.moduloInventario.servicios.productos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.Producto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaProductoPorNombre {

    private ProductoRepository productoRepository;

    public ConsultaProductoPorNombre(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public static class EntradaConsultaProductoPorNombre{
        public String nombre;
    }

    public static class ProductoConsulta{
        public String nombre;
        public String fechaRegistroProducto;


    }
    public class SalidaConsultaProductoPorNombre extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public List<ProductoConsulta> productos;

        public List<ProductoConsulta> getProductos() {
            return productos;
        }

        public void setProductos(List<ProductoConsulta> productos) {
            this.productos = productos;
        }
    }


    public SalidaConsultaProductoPorNombre logica(EntradaConsultaProductoPorNombre entrada){
        SalidaConsultaProductoPorNombre salida = new SalidaConsultaProductoPorNombre();

        if(entrada.nombre == null || entrada.nombre.trim().isEmpty()){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Parametro 'nombre' necesario para busqueda");
            salida.setErrores(+1);
            return salida;
        }

        String nombre = entrada.nombre.trim();
        List<Producto> productos = productoRepository.findByCaseInsensitivNombre(nombre);
        if(productos.isEmpty()){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje("No existen productos reegistrados con coincidencia de nombre " + nombre);
            salida.setErrores(+1);
            return salida;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<ProductoConsulta> productosConsulta = new ArrayList<>();
        for(Producto producto: productos){
            ProductoConsulta productoConsulta = new ProductoConsulta();
            productoConsulta.nombre = producto.getNombre();
            productoConsulta.fechaRegistroProducto = sdf.format(producto.getFechaRegistro());
            productosConsulta.add(productoConsulta);
        }

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Productos encontrados correctamente");
        salida.setProductos(productosConsulta);

        return salida;
    }
}
