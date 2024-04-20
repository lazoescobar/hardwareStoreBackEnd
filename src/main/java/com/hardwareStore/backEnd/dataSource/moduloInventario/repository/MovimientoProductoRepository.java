package com.hardwareStore.backEnd.dataSource.moduloInventario.repository;

import com.hardwareStore.backEnd.dataSource.moduloInventario.model.MovimientoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoProductoRepository extends JpaRepository<MovimientoProducto, Integer> {
}
