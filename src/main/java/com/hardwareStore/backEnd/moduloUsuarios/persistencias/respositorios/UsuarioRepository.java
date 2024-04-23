package com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios;

import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.UsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<UsuarioEntidad, Integer> {

    UsuarioEntidad findFirstById(Integer id);

    UsuarioEntidad findFirstByIdAndNombre(Integer id, String nombre);
}
