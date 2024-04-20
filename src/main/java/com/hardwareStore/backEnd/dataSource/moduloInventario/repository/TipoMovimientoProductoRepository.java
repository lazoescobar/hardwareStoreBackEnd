package com.hardwareStore.backEnd.dataSource.moduloInventario.repository;

import com.hardwareStore.backEnd.dataSource.moduloInventario.model.TipoMovimientoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoMovimientoProductoRepository extends JpaRepository<TipoMovimientoProducto, String> {
}
