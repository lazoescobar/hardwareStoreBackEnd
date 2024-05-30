package com.hardwareStore.backEnd.moduloPersona.persistencias.repositorios;

import com.hardwareStore.backEnd.moduloPersona.persistencias.modelos.Persona;
import com.hardwareStore.backEnd.moduloUsuarios.persistencias.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonaRepository extends JpaRepository<Persona, Integer> {

}
