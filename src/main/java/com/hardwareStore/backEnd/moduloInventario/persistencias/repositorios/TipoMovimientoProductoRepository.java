package com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios;

import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.TipoMovimientoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoMovimientoProductoRepository extends JpaRepository<TipoMovimientoProducto, String> {
}
