package com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios;

import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.HistorialNombreProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialNombreProductoRepository extends JpaRepository<HistorialNombreProducto, Integer> {
}
