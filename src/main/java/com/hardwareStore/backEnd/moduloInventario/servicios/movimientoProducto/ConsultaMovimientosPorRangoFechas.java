package com.hardwareStore.backEnd.moduloInventario.servicios.movimientoProducto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.hardwareStore.backEnd.moduleSystem.servicios.SalidaBaseService;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.MovimientoProducto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios.MovimientoProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.time.ZoneId;

@Service
public class ConsultaMovimientosPorRangoFechas {

    private final MovimientoProductoRepository movimientoProductoRepository;

    public ConsultaMovimientosPorRangoFechas(MovimientoProductoRepository movimientoProductoRepository){
        this.movimientoProductoRepository = movimientoProductoRepository;
    }


    public static class EntradaConsultaMovimientosPorRangoFechas{
        public String fechaDesde;
        public String fechaHasta;
    }

    public class InformacioMovimiento{
        public String nombreProducto;
        public String tipoMovimiento;
        public Integer cantidad;
    }

    public class SalidaConsultaMovimientosPorRangoFechas extends SalidaBaseService{
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public HashMap<String, List<InformacioMovimiento>> fechas;

        public void agregarNuevaFechaConMovimientos(String fecha, List<InformacioMovimiento> movimientos){
            if(fechas == null){
                fechas = new HashMap<>();
            }
            fechas.put(fecha, movimientos);
        }

        public HashMap<String, List<InformacioMovimiento>> getFechas() {
            return fechas;
        }
    }

    public SalidaConsultaMovimientosPorRangoFechas logica(EntradaConsultaMovimientosPorRangoFechas entrada){

        SalidaConsultaMovimientosPorRangoFechas salida = new SalidaConsultaMovimientosPorRangoFechas();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaDesde = null;
        Date fechaHasta = null;
        try {
            fechaDesde = formatter.parse(entrada.fechaDesde);
            fechaHasta = formatter.parse(entrada.fechaHasta);
        } catch (ParseException e) {}

        if(fechaDesde == null  || fechaHasta == null){
            salida.setEstado(HttpStatus.BAD_REQUEST);
            salida.setMensaje("Error con rango de fechas");
            salida.setErrores(+1);
            return salida;
        }

        List<MovimientoProducto> movimientoProductos = movimientoProductoRepository.findByFechaRegistroBetweenInclusive(fechaDesde, fechaHasta);
        if(movimientoProductos.isEmpty()){
            salida.setEstado(HttpStatus.NOT_FOUND);
            salida.setMensaje("No existen movimientos para rango de fechas");
            salida.setErrores(+1);
            return salida;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Map<LocalDate, List<MovimientoProducto>> movimientosPorFecha = movimientoProductos.stream()
                .collect(Collectors.groupingBy(movimientoProducto -> movimientoProducto.getFechaRegistro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));

        List<Map.Entry<LocalDate, List<MovimientoProducto>>> listFechas = new ArrayList<>(movimientosPorFecha.entrySet());

        listFechas.sort(Comparator.comparing(Map.Entry::getKey));

        for (Map.Entry<LocalDate, List<MovimientoProducto>> fechaMovimiento : listFechas) {
            List<MovimientoProducto> movimientos =fechaMovimiento.getValue();
            List<InformacioMovimiento> infoMovimientos = new ArrayList<>();
            for(MovimientoProducto movimientoProducto: movimientos){
                InformacioMovimiento infoMovimiento = new InformacioMovimiento();
                infoMovimiento.nombreProducto= movimientoProducto.getProducto().getNombre();
                infoMovimiento.tipoMovimiento = movimientoProducto.getTipoMovimientoProducto().getTipo();
                infoMovimiento.cantidad = movimientoProducto.getCantidad();
                infoMovimientos.add(infoMovimiento);
            }
            salida.agregarNuevaFechaConMovimientos(fechaMovimiento.getKey().toString(), infoMovimientos);
        }
        salida.setEstado(HttpStatus.OK);
        salida.setMensaje("Movimientos obtenidos correctamente");
        return salida;
    }


}
