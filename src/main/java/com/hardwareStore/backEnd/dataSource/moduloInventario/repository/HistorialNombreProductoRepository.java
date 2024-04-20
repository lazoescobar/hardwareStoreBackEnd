package com.hardwareStore.backEnd.dataSource.moduloInventario.repository;

import com.hardwareStore.backEnd.dataSource.moduloInventario.model.HistorialNombreProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialNombreProductoRepository extends JpaRepository<HistorialNombreProducto, Integer> {
}
