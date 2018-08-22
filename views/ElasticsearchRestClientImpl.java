package com.boco.mis.persist.elasticsearch.client.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;

import com.boco.mis.persist.elasticsearch.client.IElasticsearchRestClient;
import com.boco.mis.persist.elasticsearch.factory.RestClientFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class ElasticsearchRestClientImpl implements IElasticsearchRestClient {
 
    @Resource
    private RestClientFactory restClientFactory;
    
	public RestClient getRestClient() {
		
		if(true) {
			return RestClient.builder(
	                new HttpHost("39.104.110.53", 9200, "http")).build();
		}
		
		
		return restClientFactory.getRestClient();
	}

	/*
	 *   case HttpDeleteWithEntity.METHOD_NAME:
                return addRequestBody(new HttpDeleteWithEntity(uri), entity);
            case HttpGetWithEntity.METHOD_NAME:
                return addRequestBody(new HttpGetWithEntity(uri), entity);
            case HttpHead.METHOD_NAME:
                return addRequestBody(new HttpHead(uri), entity);
            case HttpOptions.METHOD_NAME:
                return addRequestBody(new HttpOptions(uri), entity);
            case HttpPatch.METHOD_NAME:
                return addRequestBody(new HttpPatch(uri), entity);
            case HttpPost.METHOD_NAME:
                HttpPost httpPost = new HttpPost(uri);
                addRequestBody(httpPost, entity);
                return httpPost;
            case HttpPut.METHOD_NAME:
                return addRequestBody(new HttpPut(uri), entity);
            case HttpTrace.METHOD_NAME:
                return addRequestBody(new HttpTrace(uri), entity);
            default:
	 * 
	 * */
	
	/**
	 * 创建索引
	 */
	public void createIndex() {
		
		/*
		 * curl -X PUT 'localhost:9200/accounts' -d '
		 * 
		 * 
		 * {
  "mappings": {
    "person": {
      "properties": {
        "user": {
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word"
        },
        "title": {
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word"
        },
        "desc": {
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word"
        }
      }
    }
  }
}'
		 * 
		 * */
		String authIndexDefine = "{\n" +
                "\t\"settings\" : {\n" +
                "        \"index\" : {\n" +
                "            \"number_of_shards\" : 6,\n" +
                "            \"number_of_replicas\" : 0\n" +
                "        }\n" +
                "    },\n" +
                "    \"mappings\": {\n" +
                "        \"default\": {\n" +
                "            \"properties\": {\n" +
                "              \t\"traceId\": {\"type\": \"keyword\"},\n" +
                "                \"traceName\": {\"type\": \"keyword\"},\n" +
                "                \"beginTimeMillis\": {\"type\": \"long\"},\n" +
                "                \"endTimeMillis\": {\"type\": \"long\"}\n" +
                "             }\n" +
                "        }\n" +
                "    }\n" +
                "}";

		HttpEntity authorIndexEntity = new NStringEntity(authIndexDefine, ContentType.APPLICATION_JSON);
		
		try {
			getRestClient().performRequest(HttpPut.METHOD_NAME, "/traces", new HashMap<String, String>(), authorIndexEntity);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
//		String authIndexDefine = "{\n" +
//                "\t\"settings\" : {\n" +
//                "        \"index\" : {\n" +
//                "            \"number_of_shards\" : 6,\n" +
//                "            \"number_of_replicas\" : 0\n" +
//                "        }\n" +
//                "    },\n" +
//                "    \"mappings\": {\n" +
//                "        \"doc\": {\n" +
//                "            \"properties\": {\n" +
//                "            \t\"id\": {\"type\": \"text\"},\n" +
//                "                \"name\": {\"type\": \"text\"},\n" +
//                "                \"sex\": {\"type\": \"text\"},\n" +
//                "                \"age\": {\"type\": \"integer\"},\n" +
//                "                \"des\":{\n" +
//                "                \t\"type\":\"text\",\n" +
//                "                \t\"analyzer\": \"ik_max_word\",\n" +
//                "\t\t\t\t\t\"search_analyzer\": \"ik_max_word\"\n" +
//                "                }\n" +
//                "            }\n" +
//                "        }\n" +
//                "    }\n" +
//                "}";
//
//		HttpEntity authorIndexEntity = new NStringEntity(authIndexDefine, ContentType.APPLICATION_JSON);
//		  
		ObjectMapper objectMapper = new ObjectMapper();
		try {
//			// 创建索引
//			getRestClient().performRequest("PUT", "/test_demo_index", new HashMap<String, String>(), authorIndexEntity);
//			
			
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
		impl.testquery();
//		impl.createIndex();
		
		impl.post(null, "traces", "default");
		
	}
	
	
}
