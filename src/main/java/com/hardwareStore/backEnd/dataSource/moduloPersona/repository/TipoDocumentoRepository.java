package com.hardwareStore.backEnd.dataSource.moduloPersona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hardwareStore.backEnd.dataSource.moduloPersona.model.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, String> {
}
