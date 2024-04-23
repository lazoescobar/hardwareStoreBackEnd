package com.hardwareStore.backEnd.moduleSystem.servicios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;


public class SalidaBaseService {

    @JsonIgnore
    private HttpStatus estado;
    private String mensaje;
    @JsonIgnore
    private int errores = 0;
    public HttpStatus getEstado() {
        return estado;
    }
    public void setEstado(HttpStatus estado) {
        this.estado = estado;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public int getErrores() {
        return errores;
    }
    public void setErrores(int errores) {
        this.errores = errores;
    }

}



