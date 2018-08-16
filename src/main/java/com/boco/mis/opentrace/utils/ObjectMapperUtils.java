package com.boco.mis.opentrace.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtils {

	static ObjectMapper objectMapper = new ObjectMapper();
	
	static {
		objectMapper.setDefaultPropertyInclusion(Include.NON_NULL);
	}
	
	public static  <T> T  toBean(Object source,Class<T> targetClass) {
		try {
			return objectMapper.readValue(objectMapper.writeValueAsBytes(source), targetClass);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
