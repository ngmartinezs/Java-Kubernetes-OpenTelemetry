package com.ws.wslogin.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ws.wslogin.domain.dto.HeadersDTO;
import com.ws.wslogin.domain.dto.RequestInactivarDTO;
import com.ws.wslogin.domain.dto.RequestRegistrarDatosDTO;
import com.ws.wslogin.domain.dto.ResponseConsultaDTO;
import com.ws.wslogin.domain.dto.ResponseDTO;
import com.ws.wslogin.domain.dto.ResponseRegistrarDTO;
import com.ws.wslogin.domain.service.DatosPrincipalesService;
import com.ws.wslogin.logsUtil.CustonExceptionAPI;
import com.ws.wslogin.persistence.repository.DatosPrincipalesRepository;


@Component
public class DatosPrincipalesServiceImpl implements DatosPrincipalesService {

    @Autowired
    DatosPrincipalesRepository datosPrincipalesRepository;

    @Autowired
    CustonExceptionAPI custonExceptionAPI;


    @Override
    public ResponseConsultaDTO consultaDatos(HeadersDTO headersDTO, String tipoDocumento, String numeroDocumento) throws Exception{
        
         ResponseConsultaDTO lResponseConsultaDTO = null;
        try {
            lResponseConsultaDTO =  datosPrincipalesRepository.consultarDatosPersona(tipoDocumento, numeroDocumento, numeroDocumento);    
            
            if(lResponseConsultaDTO != null && lResponseConsultaDTO.getDatosPersona() != null)
            {
                lResponseConsultaDTO.getResponse().setResponse("00");
                lResponseConsultaDTO.getResponse().setDescCode("OK");
                lResponseConsultaDTO.getResponse().setResponse("");
                
            }
            else
            {
                throw new CustonExceptionAPI("No se encontraron datos para la consulta", new Exception());
            }
        } catch (Exception e) {
            // TODO: handle exception
             throw new CustonExceptionAPI("Se presento un error en la ejecucion metodo  ResponseConsultaDTO consultaDatos", e);
        }
        return lResponseConsultaDTO;
    }

    @Override
    public ResponseRegistrarDTO registrarDatos(RequestRegistrarDatosDTO requestRegistrarDatosDTO) throws Exception
    {
        ResponseRegistrarDTO lResponseRegistrarDTO = null;

        System.out.println("\n Services => requestRegistrarDatosDTO: " + requestRegistrarDatosDTO.toString());

        try {
            lResponseRegistrarDTO = new ResponseRegistrarDTO();
            lResponseRegistrarDTO.setResponse(ResponseDTO.builder().build());
            lResponseRegistrarDTO.getResponse().setResponse("success");
            lResponseRegistrarDTO.getResponse().setDescCode("OK");
            lResponseRegistrarDTO.getResponse().setCodeResponse("00");

            String id = datosPrincipalesRepository.registrarDatosPersona(requestRegistrarDatosDTO);
            lResponseRegistrarDTO.setNumeroDocumento(requestRegistrarDatosDTO.getDatosPersona().getNumeroDocumento());
            lResponseRegistrarDTO.setTipoDocumento(requestRegistrarDatosDTO.getDatosPersona().getTipoDocumento());
            lResponseRegistrarDTO.setIdGenerado(id);
            
            
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            throw custonExceptionAPI.getCustonExceptionAPI("Se presento un error en la ejecucion metodo registrarDatos", e);
        }
        return lResponseRegistrarDTO;
    }

    @Override
    public ResponseDTO inactivarDatos(HeadersDTO headersDTO, RequestInactivarDTO requestInactivarDTO) throws Exception
    {
        return null;
    }
}
