package com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios;

import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, String> {

}
