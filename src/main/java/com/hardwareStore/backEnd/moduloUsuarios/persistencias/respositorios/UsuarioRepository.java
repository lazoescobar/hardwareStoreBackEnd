package com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios;

import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstById(Integer id);

    Usuario findFirstByNombreAndPass(String nombre, String pass);
}
