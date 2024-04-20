package com.hardwareStore.backEnd.dataSource.moduloUsuario.repository;

import com.hardwareStore.backEnd.dataSource.moduloUsuario.model.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, String> {
}
