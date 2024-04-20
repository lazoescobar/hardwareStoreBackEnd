package com.hardwareStore.backEnd.jpaExample;

import com.hardwareStore.backEnd.dataSource.moduloPersona.model.DocumentoIndentidad;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hardwareStore.backEnd.dataSource.moduloPersona.repository.*;

import java.util.List;

@RestController
public class UserRest {

    private final DocumentoIdentidadRepository documentoIdentidadRepository;

    public UserRest(DocumentoIdentidadRepository documentoIdentidadRepository){
        this.documentoIdentidadRepository = documentoIdentidadRepository;
    }

    @GetMapping("/user")
    public String index(){

        List<DocumentoIndentidad> documentsIdentidad = documentoIdentidadRepository.findAll();
        return "Hola mundo";
    }
}
