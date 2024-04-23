package com.hardwareStore.backEnd.moduloUsuarios.api;


import com.hardwareStore.backEnd.moduloUsuarios.servicios.tipoUsuarios.BuscarTiposUsuarios;
import com.hardwareStore.backEnd.moduloUsuarios.servicios.tipoUsuarios.BuscarTiposUsuarios.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("modulo-usuario/tipo-usuario/")
public class TipoUsuario {

    private final BuscarTiposUsuarios listarTiposUsuarios;

    public TipoUsuario(BuscarTiposUsuarios listarTiposUsuarios){
        this.listarTiposUsuarios = listarTiposUsuarios;
    }

    @GetMapping("list")
    public ResponseEntity<Object> listarTodos(Entrada entrada){
        Salida salida = listarTiposUsuarios.logica(entrada);
        return ResponseEntity.status(salida.getEstado()).body(salida);
    }
}
