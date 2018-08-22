package com.boco.mis.persist.elasticsearch.factory;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Component;

@Component("restClientFactory")
public class RestClientFactory {

	private RestClient restClient ; 
	   
    @PostConstruct
    public void init(){
    	// 读取配置实例化restClient
    	restClient = RestClient.builder(
                new HttpHost("39.104.110.53", 9200, "http")).build();
	
//    	final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials("elastic", "changeme"));
//	    
//        restClient = RestClient.builder(new HttpHost("localhost",9200,"http"))
//                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                    @Override
//                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//                    }
//                }).build();

//    	RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
//    	builder.setMaxRetryTimeoutMillis(10000);
    	
//    	RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
//    	builder.setFailureListener(new RestClient.FailureListener() {
//    	    @Override
//    	    public void onFailure(HttpHost host) {
//    	       // TODO
//    	    }
//    	});

    
    }
	
	@PreDestroy
	public void dispose(){
		try {
			if(restClient != null) {
				restClient.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RestClient getRestClient() {
		return restClient;
	}

}
