package com.ws.wslogin.persistence.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ws.wslogin.domain.dto.RequestRegistrarDatosDTO;
import com.ws.wslogin.domain.dto.ResponseConsultaDTO;
import com.ws.wslogin.logsUtil.CustonExceptionAPI;
import com.ws.wslogin.persistence.Entity.DatosPrincipalesEntity;
import com.ws.wslogin.persistence.Entity.DatosPrincipalesEntity.DatosPersona;

@Repository
public class DatosPrincipalesRepositoryImplement implements DatosPrincipalesRepository {

    private HashMap<String, DatosPrincipalesEntity> datosPrincipales;

    @Autowired
    CustonExceptionAPI custonExceptionAPI;

    @Override
    public String registrarDatosPersona(RequestRegistrarDatosDTO registrarDatosDTO) throws Exception {
        // TODO Auto-generated method stub
        String id = "";
        System.out.println("\n Persistencia => requestRegistrarDatosDTO: " + registrarDatosDTO.toString());

        try {
          
            Random random = new Random();

            if((random.nextInt(2)%2)==1)
                throw new Exception("Error de prueba");

            if(datosPrincipales == null)
                datosPrincipales = new HashMap<String,DatosPrincipalesEntity>();
            
            id = LocalDateTime.now().toString()+ String.valueOf(random.nextInt(1000));
            DatosPrincipalesEntity datosPrincipalesEntity = new DatosPrincipalesEntity();
            datosPrincipalesEntity.getDatosPersona().setTipoDocumento(registrarDatosDTO.getDatosPersona().getTipoDocumento());
            datosPrincipalesEntity.getDatosPersona().setNumeroDocumento(registrarDatosDTO.getDatosPersona().getNumeroDocumento());
            datosPrincipalesEntity.getDatosPersona().setPrimerNombre(registrarDatosDTO.getDatosPersona().getPrimerNombre());
            datosPrincipalesEntity.getDatosPersona().setSegundoNombre(registrarDatosDTO.getDatosPersona().getSegundoNombre());
            datosPrincipalesEntity.getDatosPersona().setPrimerApellido(registrarDatosDTO.getDatosPersona().getPrimerApellido());
            datosPrincipalesEntity.getDatosPersona().setSegundoApellido(registrarDatosDTO.getDatosPersona().getSegundoApellido());
            datosPrincipalesEntity.getDatosPersona().setFechaNacimiento(registrarDatosDTO.getDatosPersona().getFechaNacimiento());
            datosPrincipalesEntity.getDatosPersona().setEstadoCivil(registrarDatosDTO.getDatosPersona().getEstadoCivil());
            datosPrincipalesEntity.getDatosContacto().setTelefono(registrarDatosDTO.getDatosContacto().getTelefono());
            datosPrincipalesEntity.getDatosContacto().setCelular(registrarDatosDTO.getDatosContacto().getCelular());
            datosPrincipalesEntity.getDatosContacto().setCorreo(registrarDatosDTO.getDatosContacto().getCorreo());
            datosPrincipalesEntity.getDatosContacto().setDireccion(registrarDatosDTO.getDatosContacto().getDireccion());
            datosPrincipales.put(id, datosPrincipalesEntity);
        } catch (Exception e) {
            throw custonExceptionAPI.getCustonExceptionAPI("Se presento un error en la ejecucion metodo registrarDatosPersona", e);
        }
        return id;
    }

    @Override
    public String inactivarDatosPersona(String tipoDocumento, String numeroDocumento, String motivo) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inactivarDatosPersona'");
    }

    @Override
    public ResponseConsultaDTO consultarDatosPersona(String tipoDocumento, String numeroDocumento, String motivo) throws Exception 
    {
        DatosPrincipalesEntity datosPrincipalesEntity  = new DatosPrincipalesEntity();
        ResponseConsultaDTO responseConsultaDTO = null;
        try {
            
            if(datosPrincipales == null || datosPrincipales.size() == 0)
                return null;
            
            datosPrincipales.forEach( (String k, DatosPrincipalesEntity v) -> {
                DatosPrincipalesEntity datosPrincipalesEntityTemp = v;
                if(datosPrincipalesEntityTemp.getDatosPersona().getTipoDocumento().equals(tipoDocumento) && datosPrincipalesEntityTemp.getDatosPersona().getNumeroDocumento().equals(numeroDocumento))
                {
                    datosPrincipalesEntity.setDatosPersona(datosPrincipalesEntityTemp.getDatosPersona());
                    datosPrincipalesEntity.setDatosContacto(datosPrincipalesEntityTemp.getDatosContacto());
                }
            });

            if( datosPrincipalesEntity.getDatosPersona() == null || datosPrincipalesEntity.getDatosContacto() == null)
                return null;
            
            responseConsultaDTO = new ResponseConsultaDTO();
            responseConsultaDTO.getDatosPersona().setTipoDocumento(datosPrincipalesEntity.getDatosPersona().getTipoDocumento());	
            responseConsultaDTO.getDatosPersona().setNumeroDocumento(datosPrincipalesEntity.getDatosPersona().getNumeroDocumento());
            responseConsultaDTO.getDatosPersona().setPrimerNombre(datosPrincipalesEntity.getDatosPersona().getPrimerNombre());
            responseConsultaDTO.getDatosPersona().setSegundoNombre(datosPrincipalesEntity.getDatosPersona().getSegundoNombre());
            responseConsultaDTO.getDatosPersona().setPrimerApellido(datosPrincipalesEntity.getDatosPersona().getPrimerApellido());
            responseConsultaDTO.getDatosPersona().setSegundoApellido(datosPrincipalesEntity.getDatosPersona().getSegundoApellido());
            responseConsultaDTO.getDatosPersona().setFechaNacimiento(datosPrincipalesEntity.getDatosPersona().getFechaNacimiento());
            responseConsultaDTO.getDatosPersona().setEstadoCivil(datosPrincipalesEntity.getDatosPersona().getEstadoCivil());
            responseConsultaDTO.getDatosContacto().setTelefono(datosPrincipalesEntity.getDatosContacto().getTelefono());
            responseConsultaDTO.getDatosContacto().setCelular(datosPrincipalesEntity.getDatosContacto().getCelular());
            responseConsultaDTO.getDatosContacto().setCorreo(datosPrincipalesEntity.getDatosContacto().getCorreo());
            responseConsultaDTO.getDatosContacto().setDireccion(datosPrincipalesEntity.getDatosContacto().getDireccion());
            
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return responseConsultaDTO;
    }
    
}
