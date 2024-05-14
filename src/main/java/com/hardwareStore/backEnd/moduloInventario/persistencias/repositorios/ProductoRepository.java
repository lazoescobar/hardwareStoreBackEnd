package com.hardwareStore.backEnd.moduloInventario.persistencias.repositorios;

import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    Producto findFirstById(Integer id);

    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(concat('%', :nombre, '%'))")
    List<Producto> findByCaseInsensitivNombre(String nombre);

    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) = LOWER(:nombre)")
    Producto findFirtsByNombreIgnoreCase(@Param("nombre") String nombre);
}
