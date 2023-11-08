package com.ws.wsLogsElastic.integration;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.checkerframework.checker.units.qual.A;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Getter;

@Getter
@Configuration
public class ElasticSearchIntegation { 

    private ElasticsearchClient elasticsearchClient;

    public ElasticSearchIntegation () {
         this.elasticsearchClient = initClient();
    }

    private ElasticsearchClient initClient()
    {
        String serverUrl= "https://elk-logs-pt.es.westus2.azure.elastic-cloud.com";
        String apiKey= "VHc0bldvb0JLZ3IzSC1tbjl6TUU6OU9KdE1jcWJRT3lJTVA2VThiQWdlQQ==";
        ElasticsearchClient elasticsearchClient = null;
        try {
            RestClient restClient = RestClient.builder(
            HttpHost.create(serverUrl))
            .setDefaultHeaders(new Header[]{new BasicHeader("Authorization", "ApiKey " + apiKey)})
            .build();

            ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

            elasticsearchClient = new ElasticsearchClient(transport);
           // return elasticsearchClient;
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            throw e;
        }    

        return elasticsearchClient;
    }
}
