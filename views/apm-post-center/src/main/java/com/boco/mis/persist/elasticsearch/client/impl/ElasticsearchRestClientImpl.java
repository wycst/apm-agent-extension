package com.boco.mis.persist.elasticsearch.client.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;

import com.boco.mis.persist.elasticsearch.client.IElasticsearchRestClient;
import com.boco.mis.persist.elasticsearch.factory.RestClientFactory;
import com.boco.mis.persist.elasticsearch.index.IndexDefine;
import com.boco.mis.persist.model.ApmModelIndex;
import com.boco.mis.persist.model.Server;
import com.boco.mis.persist.model.StackTrace;
import com.boco.mis.persist.model.Trace;
import com.boco.mis.persist.model.TraceNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class ElasticsearchRestClientImpl implements IElasticsearchRestClient {
 
    @Resource
    private RestClientFactory restClientFactory;
    
    private RestClient restClient;
    
	public RestClient getRestClient() {
		if(true) {
			return restClient == null ? restClient = RestClient.builder(
	                new HttpHost("39.104.110.53", 9200, "http")).build() : restClient;
		}
		return restClientFactory.getRestClient();
	}

	public void initApmIndexs() {
		try {
			createIndex(IndexDefine.generateIndexDefine(Server.class));
			createIndex(IndexDefine.generateIndexDefine(StackTrace.class));
			createIndex(IndexDefine.generateIndexDefine(Trace.class));
			createIndex(IndexDefine.generateIndexDefine(TraceNode.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean existIndex(String indexName) {
		try {
			Response response = getRestClient().performRequest("HEAD",indexName,Collections.<String, String>emptyMap());
			return response.getStatusLine().getReasonPhrase().equals("OK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private void createIndex(IndexDefine indexDefine) throws Exception {
		String indexName = indexDefine.indexName();
		if(!existIndex(indexName)) {
			String indexDefineJson = new ObjectMapper().writeValueAsString(indexDefine);
			HttpEntity indexEntity = new NStringEntity(indexDefineJson, ContentType.APPLICATION_JSON);
			getRestClient().performRequest(HttpPut.METHOD_NAME, "/" + indexName, new HashMap<String, String>(), indexEntity);
		}
	}

	public void postAsync(Object data,String index,String type) {
		getRestClient().performRequestAsync("PUT", "/", Collections.<String, String>emptyMap(),null, new ResponseListener(){

			public void onSuccess(Response response) {
			}

			public void onFailure(Exception exception) {
			}
		});
	}
	
	public void delete(Map<String,String> params,String index,String type) {
		
		if(params == null) {
			params = new HashMap<String, String>();
			params.put("q", "id:1");
			params.put("pretty", "true");
		}
		try {
			
			Response response = getRestClient().performRequest("DELETE",  "/"+index + "/_query",params);
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void post(Object data,String index,String type) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {

			Map<String,Object> d = new HashMap<String,Object>();
			d.put("traceId", "aaaaaa11");
			d.put("traceName", "namett");
			d.put("beginTimeMillis", 10000);
			d.put("endTimeMillis", 300);
			
			HttpEntity entity = new NStringEntity(
					objectMapper.writeValueAsString(d), ContentType.APPLICATION_JSON);

			Response response = getRestClient().performRequest(HttpPost.METHOD_NAME, "/" + index + "/" + type, Collections.<String, String>emptyMap(),entity);
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testquery() {
		/*
		 *  curl 'localhost:9200/accounts/person/_search'  -d '
{
  "query" : { "match" : { "desc" : "管理" }},
  "from": 1,
  "size": 1
}'
		 * */
//		HttpEntity entity = new NStringEntity("{\n" +
//                "  \"query\": {\n" +
//                "    \"match_all\": {}\n" +
//                "  }\n" +
//                "}", ContentType.APPLICATION_JSON);

		HttpEntity entity = new NStringEntity("{\n" +
                "  \"query\": {\n" +
         //       "    \"match_all\": {}\n" +
                "    \"match\": {\"desc\" : \"管\"}\n" +
                "  }\n" +
                "  ,\"from\" : 0" +
                "  ,\"size\" : 1" +
                "}", ContentType.APPLICATION_JSON);
		
		try {
			Response response = getRestClient().performRequest("GET", "/accounts/person/_search?pretty", new HashMap<String, String>(),entity);
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public static void main(String[] args) {
		ElasticsearchRestClientImpl impl = new ElasticsearchRestClientImpl();
//		impl.delete(null, "test_demo_index", "demo");
//		impl.createIndex();
		
//		impl.post(null, "traces", "default");
		
		impl.initApmIndexs();
		
//		impl.testquery();
		
	}

	public void addApmIndex(ApmModelIndex model) {
		String indexName = model.indexName();
		String indexType = model.indexType();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			HttpEntity entity = new NStringEntity(
					objectMapper.writeValueAsString(model), ContentType.APPLICATION_JSON);
			Response response = getRestClient().performRequest(HttpPost.METHOD_NAME, "/" + indexName + "/" + indexType + "/" + model.indexId(), Collections.<String, String>emptyMap(),entity);
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
