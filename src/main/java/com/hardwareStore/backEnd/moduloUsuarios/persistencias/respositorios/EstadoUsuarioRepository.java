package com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios;

import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoUsuarioRepository extends JpaRepository<EstadoUsuario, String> {
}
