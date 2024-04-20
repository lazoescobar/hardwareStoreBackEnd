package com.hardwareStore.backEnd.dataSource.moduloInventario.repository;

import com.hardwareStore.backEnd.dataSource.moduloInventario.model.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, String> {
}
