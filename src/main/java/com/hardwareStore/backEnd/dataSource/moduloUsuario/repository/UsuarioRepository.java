package com.hardwareStore.backEnd.dataSource.moduloUsuario.repository;

import com.hardwareStore.backEnd.dataSource.moduloUsuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
