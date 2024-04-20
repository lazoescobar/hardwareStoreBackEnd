package com.hardwareStore.backEnd.dataSource.moduloPersona.repository;

import com.hardwareStore.backEnd.dataSource.moduloPersona.model.DocumentoIndentidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentoIdentidadRepository extends JpaRepository<DocumentoIndentidad, String> {

    List<DocumentoIndentidad> findByPersonaId(Integer id);

}
