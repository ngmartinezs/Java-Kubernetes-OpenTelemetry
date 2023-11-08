package com.ws.wslogin.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ws.wslogin.domain.dto.ResponseDTO;
import com.ws.wslogin.domain.service.LoginService;
import com.ws.wslogin.logsUtil.CustonExceptionAPI;
import com.ws.wslogin.persistence.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseDTO validateUser(String tipoDocumento, String numeroDocumento, String clave) {

        ResponseDTO lResponseDTO = null;
        if (tipoDocumento != null && numeroDocumento != null && clave != null) {

            if (userRepository.userActive(tipoDocumento, numeroDocumento, clave)) {
                lResponseDTO = ResponseDTO.builder()
                        .codeResponse("00")
                        .response("ok")
                        .tipoDocumento(tipoDocumento)
                        .numeroDocumento(numeroDocumento)
                        .build();
            } else {
                lResponseDTO = ResponseDTO.builder()
                        .codeResponse("03")
                        .response("Usuario o contraseña invalidos")
                        .build();
            }

        } else {
            lResponseDTO = ResponseDTO.builder()
                    .codeResponse("01")
                    .response("Informacion de Usuario invalidos")
                    .build();
        }

        return lResponseDTO;
    }

    @Override
    public ResponseDTO getEstado(String tipoDocumento, String numeroDocumento) throws Exception {

        ResponseDTO lResponseDTO = null;
        try {
            if (tipoDocumento != null && numeroDocumento != null) {

                if (userRepository.userActive(tipoDocumento, numeroDocumento)) {
                    lResponseDTO = ResponseDTO.builder()
                            .codeResponse("00")
                            .response("ok")
                            .estado("Activo")
                            .tipoDocumento(tipoDocumento)
                            .numeroDocumento(numeroDocumento)
                            .build();
                } else {
                    lResponseDTO = ResponseDTO.builder()
                            .codeResponse("04")
                            .response("Usuario No Existe o esta Inactivo")
                            .build();
                }

            } else {
                lResponseDTO = ResponseDTO.builder()
                        .codeResponse("01")
                        .response("Informacion de Usuario No Valida")
                        .build();
            }
        } catch (Exception e) {
            throw new CustonExceptionAPI("Exception getEstado",e);
        }

        return lResponseDTO;

    }

}
