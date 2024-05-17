package com.hardwareStore.backEnd.moduloUsuarios.persistencias.respositorios;

import com.hardwareStore.backEnd.moduloInventario.persistencias.modelos.Producto;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstById(Integer id);

    Usuario findFirstByNombreAndPass(String nombre, String pass);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nombre) LIKE LOWER(concat('%', :nombre, '%'))")
    List<Usuario> findByCaseInsensitivNombre(String nombre);
}
