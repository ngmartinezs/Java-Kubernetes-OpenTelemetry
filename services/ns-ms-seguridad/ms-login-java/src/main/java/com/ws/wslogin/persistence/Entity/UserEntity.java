package com.ws.wslogin.persistence.Entity;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ws.wslogin.logsUtil.CustonExceptionAPI;
import com.ws.wslogin.persistence.repository.UserRepository;

@Repository
public class UserEntity implements UserRepository {

    @Autowired
    CustonExceptionAPI custonExceptionAPI;

    private HashMap<String,String> data;

    
    public UserEntity()
    {
        data = loadData();
    }

    @Override
    public boolean userActive(String tipoDocumento, String numeroDocumento, String clave) {
        // TODO Auto-generated method stub
        String keyFind=tipoDocumento+"-"+numeroDocumento+"-"+clave;
        return data.get(keyFind) != null?true:false;
    }

    
    
    private HashMap<String,String> loadData()
    {
        HashMap<String,String> lHashMap = new HashMap<String,String>();

        lHashMap.put("CC-11390213-11111", "CC-11390213-11111");
        lHashMap.put("CC-52771628-11111", "CC-11390213-11111");
        lHashMap.put("CC-9874564-11111", "CC-11390213-11111");
        lHashMap.put("CC-4545454-11111", "CC-11390213-11111");
        lHashMap.put("CC-656565-11111", "CC-11390213-11111");
        lHashMap.put("CC-554545454-11111", "CC-11390213-11111");
        
        return lHashMap;
    }

    @Override
    public boolean userActive(String tipoDocumento, String numeroDocumento) throws Exception {
        
        String keyFind=tipoDocumento+"-"+numeroDocumento+"-";

        if(1==1)
            throw custonExceptionAPI.getCustonExceptionAPI("Excepxion Personalizada", new Exception()); //To change body of generated methods, choose Tools | Templates.
        
        boolean retorno =  data.entrySet().stream().anyMatch(x->x.getKey().startsWith(keyFind))?true:false;
        return retorno;
    }
    
}
