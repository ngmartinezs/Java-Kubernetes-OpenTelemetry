package com.ws.wslogin.persistence.repository;


import com.ws.wslogin.domain.dto.RequestRegistrarDatosDTO;
import com.ws.wslogin.domain.dto.ResponseConsultaDTO;


public interface DatosPrincipalesRepository {
    
    public String registrarDatosPersona(RequestRegistrarDatosDTO registrarDatosDTO)throws Exception;

    public String inactivarDatosPersona(String tipoDocumento, String numeroDocumento, String motivo)throws Exception;

    public ResponseConsultaDTO consultarDatosPersona(String tipoDocumento, String numeroDocumento, String motivo) throws Exception ;
}
