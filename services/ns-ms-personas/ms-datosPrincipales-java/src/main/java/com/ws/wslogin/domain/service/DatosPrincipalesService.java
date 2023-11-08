package com.ws.wslogin.domain.service;

import com.ws.wslogin.domain.dto.HeadersDTO;
import com.ws.wslogin.domain.dto.RequestInactivarDTO;
import com.ws.wslogin.domain.dto.RequestRegistrarDatosDTO;
import com.ws.wslogin.domain.dto.ResponseConsultaDTO;
import com.ws.wslogin.domain.dto.ResponseDTO;
import com.ws.wslogin.domain.dto.ResponseRegistrarDTO;


public interface DatosPrincipalesService {
    
    public ResponseConsultaDTO consultaDatos(HeadersDTO headersDTO, String tipoDocumento, String numeroDocumento) throws Exception;

    public ResponseRegistrarDTO registrarDatos(RequestRegistrarDatosDTO requestRegistrarDatosDTO) throws Exception;

    public ResponseDTO inactivarDatos(HeadersDTO headersDTO, RequestInactivarDTO requestInactivarDTO) throws Exception;
}
