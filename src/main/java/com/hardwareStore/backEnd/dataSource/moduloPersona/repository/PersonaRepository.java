package com.hardwareStore.backEnd.dataSource.moduloPersona.repository;

import com.hardwareStore.backEnd.dataSource.moduloPersona.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonaRepository extends JpaRepository<Persona, Integer> {

}
