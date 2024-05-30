package com.hardwareStore.backEnd.moduloPersona.persistencias.repositorios;

import com.hardwareStore.backEnd.moduloPersona.persistencias.modelos.DocumentoIndentidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentoIdentidadRepository extends JpaRepository<DocumentoIndentidad, String> {

    DocumentoIndentidad findByNumero(String numeroDocumento);

}
