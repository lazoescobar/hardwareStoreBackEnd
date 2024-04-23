package com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.VisualizaEnFront;

public interface VisualizaEnFrontRepository extends JpaRepository<VisualizaEnFront, Integer> {
}
