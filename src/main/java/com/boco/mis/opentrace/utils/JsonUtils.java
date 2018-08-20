package com.boco.mis.opentrace.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

//	public static  <T> Object toBean(Object source,Class<T> targetClass) {
//		return Json.parse(Json.toJsonString(source), targetClass);
//	}
	
	public static String toJsonString(Object source) {
//		return Json.toJsonString(source);
		return JSON.toJSONString(source);
	}
	
	public static Object parseJson(String source) {
//		return Json.parse(source);
		return JSON.parse(source);
	}
}
