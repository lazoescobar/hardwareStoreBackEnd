package com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios;

import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
