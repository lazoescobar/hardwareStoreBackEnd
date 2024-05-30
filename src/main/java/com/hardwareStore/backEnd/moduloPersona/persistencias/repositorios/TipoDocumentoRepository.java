package com.hardwareStore.backEnd.moduloPersona.persistencias.repositorios;

import com.hardwareStore.backEnd.moduloPersona.persistencias.modelos.DocumentoIndentidad;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hardwareStore.backEnd.moduloPersona.persistencias.modelos.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, String> {

    TipoDocumento findFirstById(String id);
}
