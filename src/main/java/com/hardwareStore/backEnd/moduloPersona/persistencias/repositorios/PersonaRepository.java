package com.hardwareStore.backEnd.moduloPersona.persistencias.repositorios;

import com.hardwareStore.backEnd.moduloPersona.persistencias.modelos.Persona;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonaRepository extends JpaRepository<Persona, Integer> {

}
