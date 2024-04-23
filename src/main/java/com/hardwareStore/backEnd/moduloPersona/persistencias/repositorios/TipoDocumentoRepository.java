package com.hardwareStore.backEnd.moduloPersona.persistencias.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hardwareStore.backEnd.moduloPersona.persistencias.modelos.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, String> {
}
