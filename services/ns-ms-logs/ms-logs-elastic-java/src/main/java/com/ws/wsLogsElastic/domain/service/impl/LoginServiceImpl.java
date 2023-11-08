package com.ws.wsLogsElastic.domain.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ws.wsLogsElastic.domain.dto.RequestDTO;
import com.ws.wsLogsElastic.domain.dto.ResponseDTO;
import com.ws.wsLogsElastic.domain.service.LoginService;
import com.ws.wsLogsElastic.integration.ElasticSearchIntegation;
import com.ws.wsLogsElastic.persistence.Entity.Logs;
import com.ws.wsLogsElastic.persistence.Entity.MessageBean;
import com.ws.wsLogsElastic.persistence.repository.LogRepository;

import co.elastic.clients.elasticsearch.ml.put_trained_model.Input;

@Service
public class LoginServiceImpl implements LoginService {


    
    @Autowired
    ElasticSearchIntegation elasticSearchIntegation;
    

    @Override
    public ResponseDTO createLog(RequestDTO requestDTO) throws Exception {
        // TODO Auto-generated method stub
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            /*LogEntity logEntity = LogEntity.builder()
                    .message("Test")
                    .build();
            logRepository.save(logEntity);*/

            MessageBean message = MessageBean.builder()
                    .typeDocPerson("")
                    .numeDocIdPerson("")
                    .request("{idRequest:35}")
                    .response("{idResponse:36}")
                    .codeError("200")
                    .build();
            
           
            ObjectMapper mapper = new ObjectMapper();
            Logs logs = Logs.builder()
                    .reqId("1")
                    .timestamp(LocalDateTime.now().toString())
                    .message(message)
                    .build();

            String json = mapper.writeValueAsString(logs);                    
            System.out.println("json: "+json);
            InputStream stream = new ByteArrayInputStream(json.getBytes());
          // elasticSearchIntegation.getElasticsearchClient().indices().create(i -> i.index("logs-v8"));
            elasticSearchIntegation.getElasticsearchClient().index(i -> i.index(".ds-logs-apm.app.ms_login_java_ws_security_v1_wslogin_authentication_valide-default-2023.09.01-000001")
            .withJson(stream));

            responseDTO.setCodeResponse("0");
            responseDTO.setDescCode("success");
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseDTO.setCodeResponse("01");
            responseDTO.setDescCode("Error Registrando Log");
        }
        return responseDTO;
    }

}
