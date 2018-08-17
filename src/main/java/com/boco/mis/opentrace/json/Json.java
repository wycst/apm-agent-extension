package com.boco.mis.opentrace.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.boco.mis.opentrace.data.server.Database;
import com.boco.mis.opentrace.reflect.AsmInvoke;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
	
	private static String toValueString(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return "\"" + value + "\"";
		}
		return value.toString();
	}
	
	public static String toJsonString(Object obj) {
		if (obj == null) {
			throw new JsonException("JSON conversion exception : source null ");
		}

		StringBuffer content = new StringBuffer();
		Class<?> clazz = obj.getClass();

		if (clazz.isArray()) {
			// 数组类
			content.append("[");
			int length = Array.getLength(obj);
			for (int i = 0; i < length; i++) {
				Object value = Array.get(obj, i);
				if (value != null) {
					value = value instanceof CharSequence ? "\"" + value + "\"" : toJsonString(value);
				} else {
				}
				content.append(value);
				if (i != (length - 1)) {
					content.append(",");
				}
			}
			content.append("]");
		} else if (Collection.class.isAssignableFrom(clazz)) {
			// 接口类
			content.append("[");
			Collection<?> collect = (Collection<?>) obj;
			String[] items = new String[collect.size()];
			int i = 0;
			for (Object item : collect) {
				if (item == null) {
					items[i++] = null;
				} else {
					items[i++] = item instanceof CharSequence ? "\"" + item + "\"" : toJsonString(item);
				}
			}
			content.append(join(items, ","));
			content.append("]");
		} else if (Map.class.isAssignableFrom(clazz)) {
			// map类
			// 调用map的get获取
			content.append("{");

			Map<Object,Object> map = (Map<Object, Object>) obj;
			String[] items = new String[map.size()];
			int i = 0;
			for (Object key : map.keySet()) {
				Object value = map.get(key);
				if(value != null) {
					value = value instanceof CharSequence ? "\"" + value + "\"" : toJsonString(value);
				}
				items[i++] = "\"" + key + "\":" + value;
			}

			content.append(join(items, ","));
			content.append("}");
		} else if (clazz.isPrimitive() || Number.class.isAssignableFrom(clazz)
				|| (clazz == String.class)) {
			// 基本类型，number，字符串都返回字符串
			return toValueString(obj);
		} else if (Date.class.isAssignableFrom(clazz)) {
			// 日期类型的如何转换
		} else {
			// 其他类型调用 get方法取
			content.append("{");

			List<String> methodNames = AsmInvoke.getNoneParamMethodNames(clazz);
			boolean deleteLastDot = false;
			for (String methodName : methodNames) {
				if (methodName.length() < 4) {
					continue;
				}
				if (!methodName.startsWith("get")) {
					continue;
				}
				String property = methodName.substring(3, 4).toLowerCase()
						+ methodName.substring(4);
				Object value = AsmInvoke.invoke(obj, clazz, methodName);
				if (value != null) {
					value = value instanceof CharSequence ? "\"" + value + "\"" : toJsonString(value);
				} else {
					// 假设忽略空的属性
					continue;
				}
				content.append("\"" + property + "\":" + value);
				content.append(",");
				deleteLastDot = true;
			}
			if (deleteLastDot) {
				content.deleteCharAt(content.lastIndexOf(","));
			}
			content.append("}");
		}
		
		return content.toString();
	}

	private static String join(String[] items, String string) {
		StringBuffer sb = new StringBuffer();
		int len = items.length;
		for (int i = 0; i < len; i++) {
			sb.append(items[i]);
			if (i != (len - 1)) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public <T> T toObject(String json, Class<T> clazz) {
		
		if(json == null) 
			return null;
		
		// linkMap记下字符串中每个{和}的位置，{值计为1，}计为-1
	    // 大括号值设置为1和-1
		Map<Integer, Integer> map1 = new LinkedHashMap<Integer, Integer>();
		// 中挂号值设置为1和-1
		Map<Integer, Integer> map2 = new LinkedHashMap<Integer, Integer>();
		// , 值设置为2
		Map<Integer, Integer> map3 = new LinkedHashMap<Integer, Integer>();
	    int index = 0;
	    for(char c : json.toCharArray()) {
	    	if(c == '{') {
	    		map1.put(index, 1);
	    	} else if(c == '}'){
	    		map1.put(index, -1);
	    	} else if(c == '[') {
	    		map2.put(index, 1);
	    	} else if(c == ']'){
	    		map2.put(index, -1);
	    	} else if(c == ',') {
	    		map3.put(index, 2);
	    	}
	    	index++;
	    }
		
		if(json.matches("{.*}")) {
			// 对象
			// {\"children\":[{\"name\":\"a1\"},{\"name\":\"a2\"}],\"name\":\"a\"}
			// 找,判断一个属性结束，标志：{+} && [+] = 0
			// 
			
		} else if(json.matches("[.*]")) {
		    // 数组
			List<Object> list = new ArrayList<Object>();
		
	    	int total = 0;
	    	int fromIndex = -1;
	    	int endIndex = -1;
	    	// 遍历linkMap时，值相加，直到=0即匹配到	
	    	for(Integer key : map1.keySet()) {
	    		Integer value = map1.get(key);
	    		if(value == 1 && fromIndex == -1) {
	    			fromIndex = key;
	    		}
	    		if(value == -1) {
	    			endIndex = key;
	    		} 
	    		total += value;
	    		if(total == 0) {
	    			// 第一个元素串 {}
	    			String itemStr = json.substring(fromIndex, endIndex + 1);
	    			list.add(toObject(itemStr,null));
	    		}
	    	}
		} else {
			throw new JsonException(" parseJson error ");
		}
		
		return null;
	}

	public static void main(String[] args) throws JsonProcessingException {
		
		HashMap target = new HashMap();
		
		List childList = new ArrayList();
		
		HashMap child1 = new HashMap();
		child1.put("name", "c1");
		childList.add(child1);
		
		HashMap child2 = new HashMap();
		child2.put("name", "c2");
		childList.add(child2);
		
		target.put("children", childList);
		target.put("name", "zhangsan");

		List list = new ArrayList();
		list.add(target);
		list.add("asdf");
		
		
//		System.out.println(toJsonString(list));
//		System.out.println(JSONArray.toJSONString(list));
		
		String jsonStr = "[{\"children\":[{\"name\":\"a1\"},{\"name\":\"a2\"}],\"name\":\"a\"},{\"children\":[{\"name\":\"b1\"},{\"name\":\"b2\"}],\"name\":\"b\"}]";
		
	    // linkMap记下字符串中每个{和}的位置，{值计为1，}计为-1
	    // 遍历linkMap时，值相加，直到=0即匹配到
	    Map<Integer, Integer> map1 = new LinkedHashMap<Integer, Integer>();
	    Map<Integer, Integer> map2 = new LinkedHashMap<Integer, Integer>();
	    int index = 0;
	    for(char c : jsonStr.toCharArray()) {
	    	if(c == '{') {
	    		map1.put(index, 1);
	    	} else if(c == '}'){
	    		map1.put(index, -1);
	    	}
	    	if(c == '[') {
	    		map2.put(index, 1);
	    	} else if(c == ']'){
	    		map2.put(index, -1);
	    	}
	    	index++;
	    }
	    
	    long ss1 = System.currentTimeMillis();
	    for(int j = 0 ; j < 100000; j ++) {
	    	
	    	int total = 0;
	    	int fromIndex = -1;
	    	int endIndex = -1;
	    	for(Integer key : map1.keySet()) {
	    		Integer value = map1.get(key);
	    		if(value == 1 && fromIndex == -1) {
	    			fromIndex = key;
	    		}
	    		if(value == -1) {
	    			endIndex = key;
	    		} 
	    		total += value;
	    		if(total == 0) {
	    			// 第一个元素串
	    			System.out.println(jsonStr.substring(fromIndex, endIndex + 1));
	    			String itemStr = jsonStr.substring(fromIndex, endIndex + 1);
	    			
	    			fromIndex = -1;
	    		}
	    	}
	    }
	    
	    long ss2 = System.currentTimeMillis();
	    System.out.println(ss2 - ss1 );
	    
//		if(true) 
//			return ;
		
		
		Database database = new Database();
		database.setHost("123");
		database.setUser("dbserver");

//		target.put("database", database);

//		for (int i = 0; i < 1000; i++) {
//			target.put("key_" + i, "value_" + i);
//		}
//
//		//
		for (int i = 0; i < 1; i++) {
			target.put("keynumber_" + i, i);
		}

		long s = System.currentTimeMillis();
		String json = null;

		for (int i = 0; i < 10000; i++) {
			// database = new Database();
			database.setHost("123" + i);
			database.setUser("dbserver" + i);
			json = new ObjectMapper().writeValueAsString(target);
		}

		long e = System.currentTimeMillis();
		System.out.println(" jackson " + (e - s));
		System.out.println(" ObjectMapper " + json);

		long s3 = System.currentTimeMillis();
		String json3 = null;

		for (int i = 0; i < 100000000; i++) {
			database = new Database();
			database.setHost("123" + i);
			database.setUser("dbserver" + i);
			json3 = JSON.toJSONString(target);
		}

		long e3 = System.currentTimeMillis();
		System.out.println(" fastjson " + (e3 - s3));
		System.out.println(" fastjson " + json3);

		long s1 = System.currentTimeMillis();
		String json2 = null;

		for (int i = 0; i < 10000; i++) {
			database = new Database();
			database.setHost("123" + i);
			database.setUser("dbserver" + i);
			json2 = toJsonString(target);
			
		}

		long e1 = System.currentTimeMillis();
		System.out.println(" toJsonString " + (e1 - s1));
		System.out.println(" toJsonString " + json2);
	}
}
