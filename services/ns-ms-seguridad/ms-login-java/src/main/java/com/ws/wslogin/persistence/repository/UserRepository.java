package com.ws.wslogin.persistence.repository;

import org.springframework.stereotype.Repository;

public interface UserRepository {

    public boolean userActive(String tipoDocumento, String numeroDocumento, String clave);

    public boolean userActive(String tipoDocumento, String numeroDocumento)throws Exception;
    
}
