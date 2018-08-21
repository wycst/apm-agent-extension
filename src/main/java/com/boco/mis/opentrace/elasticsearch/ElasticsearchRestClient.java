package com.boco.mis.opentrace.elasticsearch;

import java.io.IOException;
import java.util.Collections;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

public class ElasticsearchRestClient {

	static  RestClient restClient = RestClient.builder(
            new HttpHost("39.104.110.53", 9200, "http")).build();
	
	public ElasticsearchRestClient() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// 访问 http://39.104.110.53:9200/
		Response response = restClient.performRequest("GET", "/", Collections.singletonMap("pretty", "true"));
        System.out.println(EntityUtils.toString(response.getEntity()));
	            
        // 
        response = restClient.performRequest("HEAD","/test_demo_index/demo",Collections.<String, String>emptyMap());
        System.out.println(response.getStatusLine().getReasonPhrase().equals("OK"));
		
        restClient.close();
	}

}
