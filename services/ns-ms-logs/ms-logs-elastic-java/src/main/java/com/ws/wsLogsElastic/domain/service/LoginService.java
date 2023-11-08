package com.ws.wsLogsElastic.domain.service;

import com.ws.wsLogsElastic.domain.dto.RequestDTO;
import com.ws.wsLogsElastic.domain.dto.ResponseDTO;

public interface LoginService {

    public ResponseDTO createLog(RequestDTO requestDTO ) throws Exception;

}
