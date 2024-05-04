package com.hardwareStore.backEnd.moduloInventario.servicios.productos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.MovimientoProducto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.Producto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ConsultarProductos {

    private ProductoRepository productoRepository;

    public ConsultarProductos(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public static class EntradaConsultarProductos{
        public String nombre;
        public Boolean todos;
    }

    public class ProductoConsulta {
        public String nombre;
        public String fechaRegistroProducto;
        public String fechaUltimoIngreso;
        public String fechaUltimoEngreso;
        public Integer stockActual;
    }


    public class SalidaConsultarProductos extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public List<ProductoConsulta> productos;

        public List<ProductoConsulta> getProductos() {
            return productos;
        }

        public void setProductos(List<ProductoConsulta> productos) {
            this.productos = productos;
        }
    }


    public SalidaConsultarProductos Logica(EntradaConsultarProductos entrada){

        SalidaConsultarProductos salida = new SalidaConsultarProductos();
        boolean busquedaRealizada = false;
        boolean existenProductos = false;

        List<Producto> productos = new ArrayList<>();

        if((entrada.nombre == null || entrada.nombre.isEmpty()) && (entrada.todos == null || !entrada.todos)){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje("No se encontaron productos");
            salida.setErrores(+1);
            return  salida;
        }

        if(entrada.todos){
            productos.addAll(productoRepository.findAll());
            if(!productos.isEmpty()){
                existenProductos = true;
            }
            busquedaRealizada = true;
        }


        if(!busquedaRealizada){
            productos.addAll(productoRepository.findByNombreLike(entrada.nombre));
            if(!productos.isEmpty()){
                existenProductos = true;
            }
        }

        if(!existenProductos){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje("No se encontaron productos");
            salida.setErrores(+1);
            return  salida;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<ProductoConsulta> productoConsultas = new ArrayList<>();
        for(Producto producto: productos){
            ProductoConsulta productoConsulta = new ProductoConsulta();
            productoConsulta.nombre = producto.getNombre();
            productoConsulta.fechaRegistroProducto = sdf.format(producto.getFechaRegistro());

            List<MovimientoProducto> movimientosProducto = producto.getMovimientosProducto();

            if(!movimientosProducto.isEmpty()){

                //StockActual
                if(movimientosProducto.size() > 1){
                    Optional<MovimientoProducto> productoStockActual = movimientosProducto.stream()
                            .max((objMov1, objMov2) -> Integer.compare(objMov1.getId(), objMov2.getId()));

                    MovimientoProducto movimientoProductoStockActual = productoStockActual.get();
                    productoConsulta.stockActual = movimientoProductoStockActual.getCantidadPosterior();

                }
                else{
                    MovimientoProducto movimientoProductoStockActual = movimientosProducto.get(0);
                    productoConsulta.stockActual = movimientoProductoStockActual.getCantidadPosterior();
                    if(movimientoProductoStockActual.getTipoMovimientoProducto().getId().equals("ING")){
                        productoConsulta.fechaUltimoIngreso = sdf.format(movimientoProductoStockActual.getFechaRegistro());
                    }
                    else {
                        productoConsulta.fechaUltimoIngreso = sdf.format(movimientoProductoStockActual.getFechaRegistro());
                    }
                    productoConsultas.add(productoConsulta);
                    continue;
                }

                //Verificamos los ingresos
                List<MovimientoProducto> movimientosIngreso = movimientosProducto.stream()
                        .filter(movEgreso -> movEgreso.getTipoMovimientoProducto().getId().equals("ING"))
                        .collect(Collectors.toList());

                if(!movimientosIngreso.isEmpty()){
                    if(movimientosIngreso.size() > 1){
                        Optional<MovimientoProducto> movimientoIngreso = movimientosIngreso.stream()
                                .max((ObjIngreso1, ObjIngreso2) -> ObjIngreso1.getId() - ObjIngreso2.getId());
                        MovimientoProducto movIngreso = movimientoIngreso.get();
                        productoConsulta.fechaUltimoIngreso = sdf.format(movIngreso.getFechaRegistro());
                    }
                    else{
                        MovimientoProducto movIngreso = movimientosIngreso.get(0);
                        productoConsulta.fechaUltimoIngreso = sdf.format(movIngreso.getFechaRegistro());
                    }
                }

                //Verificamos los egresos
                List<MovimientoProducto> movimientosEgreso = movimientosProducto.stream()
                        .filter(movEgreso -> movEgreso.getTipoMovimientoProducto().getId().equals("EGR"))
                        .collect(Collectors.toList());

                if(!movimientosEgreso.isEmpty()){
                    if(movimientosEgreso.size() > 1){
                        Optional<MovimientoProducto> movimientoEgreso = movimientosEgreso.stream()
                                .max((ObjEgreso1, ObjEgreso2) -> ObjEgreso1.getId() - ObjEgreso2.getId());
                        MovimientoProducto movEgreso = movimientoEgreso.get();
                        productoConsulta.fechaUltimoEngreso = sdf.format(movEgreso.getFechaRegistro());
                    }
                    else{
                        MovimientoProducto movEgreso = movimientosEgreso.get(0);
                        productoConsulta.fechaUltimoEngreso = sdf.format(movEgreso.getFechaRegistro());
                    }
                }
            }
            else {
                productoConsulta.stockActual = 0;
            }
            productoConsultas.add(productoConsulta);
        }

        if(entrada.nombre!=null && !entrada.nombre.isEmpty()){
            List<ProductoConsulta> productosFiltrados = productoConsultas.stream()
                                                                        .filter(productoConsulta -> productoConsulta.nombre.toLowerCase().contains(entrada.nombre.toLowerCase()))
                                                                        .collect(Collectors.toList());
            if(productosFiltrados.isEmpty()){
                salida.setEstado(HttpStatus.NOT_FOUND);
                salida.setMensaje("No se encontaron productos");
                salida.setErrores(+1);
                return  salida;
            }
            else{
                salida.setProductos(productosFiltrados);
            }
        }
        else{
            salida.setProductos(productoConsultas);
        }

        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Productos obtenidos correctamente");

        return salida;
    }
}
