package com.hardwareStore.backEnd.moduloInventario.servicios.productos;

import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.Producto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.TipoProducto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.ProductoRepository;
import com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto.RegistrarNuevoIngresoEgresoProducto;
import com.hardwareStore.backEnd.moduloInventario.servicios.tipoProductos.ObtenerTipoProductos;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.usuarios.BuscarUsuarioPorId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.HashMap;


@Service
public class RegistrarNuevoProducto {

    @Autowired
    private final ProductoRepository productoRepository;
    @Autowired
    private BuscarUsuarioPorId buscarUsuarioPorId;
    @Autowired
    private  ObtenerTipoProductos obtenerTipoProductos;
    @Autowired
    private  RegistrarNuevoIngresoEgresoProducto registrarNuevoIngresoEgresoProducto;
    @Autowired
    public RegistrarNuevoProducto(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }


    public static class EntradaRegistrarNuevoProducto  {
        public Integer idUsuario;
        public String nombre;
        public Integer cantidad;
        public String tipoProducto;
    }


    public SalidaBaseService logica(EntradaRegistrarNuevoProducto entrada){

        SalidaBaseService salida = new SalidaBaseService();
        if(entrada.idUsuario == null || entrada.idUsuario == 0){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Parametro 'idUsuario' no puede ser null o 0");
            salida.setErrores(+1);
            return salida;
        }

        if(entrada.nombre == null || entrada.nombre.trim().isEmpty()){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Parametro 'nombre' no puede ser null o vacio");
            salida.setErrores(+1);
            return salida;
        }

        if(entrada.tipoProducto == null || entrada.tipoProducto.trim().isEmpty()){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Parametro 'tipoProducto' no puede ser null o vacio, ademas contener valor igual a CAJ o UNI");
            salida.setErrores(+1);
            return salida;
        }

        if(entrada.cantidad == null || entrada.cantidad == 0){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Parametro 'cantidad' no puede ser null o menor o igual que 0");
            salida.setErrores(+1);
            return salida;
        }

        BuscarUsuarioPorId.SalidaBuscarUsuarioPorId servicioaBuscarUsuarioPorId = buscarUsuarioPorId.logica(entrada.idUsuario);
        if(servicioaBuscarUsuarioPorId.getErrores() > 0){
            salida.setEstado(servicioaBuscarUsuarioPorId.getEstado());
            salida.setMensaje(servicioaBuscarUsuarioPorId.getMensaje());
            salida.setErrores(servicioaBuscarUsuarioPorId.getErrores());
            return salida;
        }

        Usuario usuario = servicioaBuscarUsuarioPorId.getUsuario();

        ObtenerTipoProductos.SalidaObtenerTipoProductos servicioObtenerTipoProductos = obtenerTipoProductos.logica();
        if(servicioObtenerTipoProductos.getErrores() > 0){
            salida.setEstado(servicioObtenerTipoProductos.getEstado());
            salida.setMensaje(servicioObtenerTipoProductos.getMensaje());
            salida.setErrores(+servicioObtenerTipoProductos.getErrores());
            return salida;
        }

        HashMap<String, TipoProducto> hashTiposProductos = servicioObtenerTipoProductos.getTiposProductos();
        TipoProducto tipoProducto = hashTiposProductos.get(entrada.tipoProducto);
        if(tipoProducto == null){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje("No existe tipo de producto");
            salida.setErrores(+1);
            return salida;
        }

        Producto productoExist = productoRepository.findFirtsByNombreIgnoreCase(entrada.nombre.trim());
        if(productoExist != null){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Ya existe un producto registrado con nombre " + entrada.nombre);
            salida.setErrores(+1);
            return salida;
        }

        Producto productoRegistrar = new Producto();
        productoRegistrar.setNombre(entrada.nombre);
        productoRegistrar.setUsuario(usuario);
        productoRegistrar.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
        productoRegistrar.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
        productoRegistrar.setTipoProducto(tipoProducto);;

        transaccion(salida, entrada, productoRegistrar, usuario);

        return salida;
    }


    @Transactional
    public void transaccion(SalidaBaseService salida, EntradaRegistrarNuevoProducto entrada, Producto productoRegistrar, Usuario usuario){

        try{
            Producto productoRegistrado = productoRepository.saveAndFlush(productoRegistrar);
            RegistrarNuevoIngresoEgresoProducto.EntadaRegistrarNuevoIngresoEgresoProducto entadaRegistrarNuevoIngresoEgresoProducto = new RegistrarNuevoIngresoEgresoProducto.EntadaRegistrarNuevoIngresoEgresoProducto();
            entadaRegistrarNuevoIngresoEgresoProducto.idUsuario = usuario.getId();
            entadaRegistrarNuevoIngresoEgresoProducto.cantidad = entrada.cantidad;
            entadaRegistrarNuevoIngresoEgresoProducto.tipoMovimiento = "ING";
            RegistrarNuevoIngresoEgresoProducto.SalidaRegistrarNuevoIngresoEgresoProducto servicioRegistrarNuevoIngresoEgresoProducto = registrarNuevoIngresoEgresoProducto.logica(productoRegistrado.getId(), entadaRegistrarNuevoIngresoEgresoProducto);
            if(servicioRegistrarNuevoIngresoEgresoProducto.getErrores() > 0){
                salida.setEstado(servicioRegistrarNuevoIngresoEgresoProducto.getEstado());
                salida.setMensaje(servicioRegistrarNuevoIngresoEgresoProducto.getMensaje());
                salida.setErrores(servicioRegistrarNuevoIngresoEgresoProducto.getErrores());
                return;
            }

            salida.setEstado(HttpStatus.OK);
            salida.setMensaje("Producto registrado correctamente");

        }catch (Exception e){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Problemas al registrar productuo " + productoRegistrar.getNombre());
            salida.setErrores(+1);
        }
    }


}
