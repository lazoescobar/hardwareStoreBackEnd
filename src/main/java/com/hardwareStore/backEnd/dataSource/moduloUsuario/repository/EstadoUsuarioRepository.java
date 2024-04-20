package com.hardwareStore.backEnd.dataSource.moduloUsuario.repository;

import com.hardwareStore.backEnd.dataSource.moduloUsuario.model.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoUsuarioRepository extends JpaRepository<EstadoUsuario, String> {
}
