package com.ws.wslogin.domain.service;

import com.ws.wslogin.domain.dto.ResponseDTO;

public interface LoginService {
    
    public ResponseDTO validateUser(String tipoDocumento, String numeroDocumento, String clave);

    public ResponseDTO getEstado(String tipoDocumento, String numeroDocumento) throws Exception;
}
