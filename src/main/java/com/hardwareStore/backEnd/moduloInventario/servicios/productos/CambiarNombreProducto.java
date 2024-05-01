package com.hardwareStore.backEnd.moduloInventario.servicios.productos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.HistorialNombreProducto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.Producto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.HistorialNombreProductoRepository;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.ProductoRepository;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.BuscarUsuarioPorId;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.BuscarUsuarioPorId.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Objects;

@Service
public class CambiarNombreProducto {

    private final ProductoRepository productoRepository;

    private final HistorialNombreProductoRepository historialNombreProductoRepository;

    @Autowired
    BuscarUsuarioPorId buscarUsuarioPorId;

    @Autowired
    BuscarProductoPorId buscarProductoPorId;

    public CambiarNombreProducto(ProductoRepository productoRepository, HistorialNombreProductoRepository historialNombreProductoRepository){
        this.productoRepository = productoRepository;
        this.historialNombreProductoRepository = historialNombreProductoRepository;
    }

    public static class EntradaCambiarNombreProducto{
        public Integer idUsuario;
        public Integer idProducto;
        public String nuevoNombre;
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

    public class SalidaambiarNombreProducto extends SalidaBaseService {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        BuscarProductoPorId.InfoProducto infoProducto;

        public BuscarProductoPorId.InfoProducto getInfoProducto() {
            return infoProducto;
        }

        public void setInfoProducto(BuscarProductoPorId.InfoProducto infoProducto) {
            this.infoProducto = infoProducto;
        }
    }

    public SalidaambiarNombreProducto logica(EntradaCambiarNombreProducto entrada){

        SalidaambiarNombreProducto salida = new SalidaambiarNombreProducto();

        SalidaBuscarUsuarioPorId servicioaBuscarUsuarioPorId = buscarUsuarioPorId.logica(entrada.idUsuario);
        if(servicioaBuscarUsuarioPorId.getErrores() > 0){
            salida.setEstado(servicioaBuscarUsuarioPorId.getEstado());
            salida.setMensaje(servicioaBuscarUsuarioPorId.getMensaje());
            salida.setErrores(servicioaBuscarUsuarioPorId.getErrores());
            return salida;
        }
        Usuario usuario = servicioaBuscarUsuarioPorId.getUsuario();

        BuscarProductoPorId.SalidaBuscarProductoPorId servicioBuscarProductoPorId = buscarProductoPorId.Logica(entrada.idProducto);
        if(servicioBuscarProductoPorId.getErrores() > 0){
            salida.setEstado(servicioBuscarProductoPorId.getEstado());
            salida.setMensaje(servicioBuscarProductoPorId.getMensaje());
            salida.setErrores(servicioBuscarProductoPorId.getErrores());
            return salida;
        }

        if(entrada.nuevoNombre == null || entrada.nuevoNombre.isEmpty()){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Nuevo nombre no puede ser vacio");
            salida.setErrores(+1);
            return salida;
        }

        Producto producto = productoRepository.findFirstById(entrada.idProducto);
        if(Objects.equals(producto.getNombre(), entrada.nuevoNombre.trim())){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Nuevo nombre debe ser distinto al nombre actual");
            salida.setErrores(+1);
            return salida;
        }

        boolean resultadoTransaccion = transaccionEntidades(entrada, salida, producto, usuario);
        if(resultadoTransaccion){
            BuscarProductoPorId.SalidaBuscarProductoPorId servicioBuscarProductoActualizadoPorId = buscarProductoPorId.Logica(entrada.idProducto);
            if(servicioBuscarProductoActualizadoPorId.getErrores() == 0 ){
                salida.setInfoProducto(servicioBuscarProductoActualizadoPorId.getInfoProducto());
            }
        }

        return salida;
    }

    @Transactional
    private boolean transaccionEntidades(EntradaCambiarNombreProducto entrada, SalidaBaseService salida, Producto producto, Usuario usuario){
        boolean todoCorrecto = false;
        try{
            String nombreAntiguo = producto.getNombre();
            producto.setNombre(entrada.nuevoNombre);
            producto.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
            productoRepository.save(producto);

            HistorialNombreProducto historialNombreProducto = new HistorialNombreProducto();
            historialNombreProducto.setNombreAntiguo(nombreAntiguo);
            historialNombreProducto.setNombreActual(entrada.nuevoNombre);
            historialNombreProducto.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
            historialNombreProducto.setProducto(producto);
            historialNombreProducto.setUsuario(usuario);
            historialNombreProductoRepository.save(historialNombreProducto);

            salida.setEstado(HttpStatus.OK);
            salida.setMensaje("Nombre de producto actualizado correctamente");
            todoCorrecto = true;
        }catch (Exception e){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("No se pudo actualizar nombre de producto");
            salida.setErrores(+1);
        }
        return todoCorrecto;
    }
}
