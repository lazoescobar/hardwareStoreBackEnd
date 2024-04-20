package com.hardwareStore.backEnd.dataSource.moduloInventario.repository;

import com.hardwareStore.backEnd.dataSource.moduloInventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
