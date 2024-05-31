package com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios;

import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.MovimientoProducto;
import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovimientoProductoRepository extends JpaRepository<MovimientoProducto, Integer> {

    @Query(value = "SELECT mp FROM MovimientoProducto mp WHERE mp.id = (SELECT MAX(mp1.id) FROM MovimientoProducto mp1 WHERE mp1.producto.id = :id) ")
    MovimientoProducto findTopByProductoId(Integer id);
    List<MovimientoProducto> findByProducto(Producto producto);
    @Query("SELECT m FROM MovimientoProducto m WHERE DATE(m.fechaRegistro) BETWEEN :fechaDesde AND :fechaHasta")
    List<MovimientoProducto> findByFechaRegistroBetweenInclusive(@Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);
}

