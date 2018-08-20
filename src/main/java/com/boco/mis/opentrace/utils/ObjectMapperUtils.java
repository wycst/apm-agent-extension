package com.boco.mis.opentrace.utils;

import com.boco.mis.opentrace.json.Json;

public class ObjectMapperUtils {

	public static  <T> Object toBean(Object source,Class<T> targetClass) {
		return Json.parse(Json.toJsonString(source), targetClass);
	}
	
}
