package com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios;

import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.MovimientoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoProductoRepository extends JpaRepository<MovimientoProducto, Integer> {
}
