package com.boco.mis.opentrace.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.boco.mis.opentrace.json.asm.MethodInfo;
import com.boco.mis.opentrace.json.exceptions.JsonException;
import com.boco.mis.opentrace.json.util.JsonHelper;
import com.boco.mis.opentrace.reflect.AsmInvoke;

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
			return ((Date)obj).getTime() + "";
		} else {
			// 其他类型调用 get方法取
			content.append("{");
			List<String> methodNames = JsonHelper.getNoneParamMethodNames(clazz);
			boolean deleteLastDot = false;
			for (String methodName : methodNames) {
				
				if (methodName.length() < 3) {
					continue;
				}
				if (!methodName.startsWith("get") && !methodName.startsWith("is")) {
					continue;
				}
				int subIndex = 3;
				if(methodName.startsWith("is")) {
					subIndex = 2;
				}
				// 获取getter方法的序列信息
				MethodInfo methodInfo = JsonHelper.getGetterMethodInfo(clazz,methodName);
				if(methodInfo == null || !methodInfo.isGetter() || methodInfo.isIgnore()) {
					continue;
				}
				Object value = AsmInvoke.invoke(obj, clazz, methodName);
				String property = methodName.substring(subIndex, subIndex + 1).toLowerCase()
						+ methodName.substring(subIndex + 1);
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

	/**
	 * 判断是否json字符串（不做校验）
	 * @param source
	 * @return
	 */
	private static boolean mathesJson(String source) {
		if(source == null) return false;
		return (source.startsWith("{") && source.endsWith("}")) || (source.startsWith("[") && source.endsWith("]"));
	}
	
	/**
	 * 判断是否为number
	 * @param source
	 * @param isFloat
	 * @return
	 */
	private static boolean isNumber(String source,boolean isFloat) {
		if(source == null)
		    return false;
		int len = source.length();
		int dotCount = 0;
		for(int i = 0 ; i < len ; i ++) {
			char c = source.charAt(i);
			if(!Character.isDigit(c)) {
				if(i == 0 && (c == '-' || c == '+') ) {
					continue;
				} else {
					if(isFloat && c == '.' && dotCount ++ < 1 && i != len - 1) {
						continue;
					}
				}
				return false;
			}
		}
		return true;
	}
	
	private static String parseProperty(String propertyName) {
		if(propertyName == null) {
			return null;
		}
		propertyName = propertyName.trim();
		if(propertyName.startsWith("\"") && propertyName.endsWith("\"")) {
			return propertyName.substring(1, propertyName.length() - 1);
		} else {
			
		}
		return propertyName;
	}
	
	private static Object parseValue(String valueStr) {
		return parseValue(valueStr,null);
	}
	
	private static Object parseValue(String valueStr,Class clazz) {
		valueStr = valueStr.trim();
        if(mathesJson(valueStr)) {
        	return parse(valueStr,clazz);
        }
        if(valueStr.startsWith("\"") && valueStr.endsWith("\"")) {
        	return valueStr.substring(1, valueStr.length() - 1);
        } else if(isNumber(valueStr, false)) {
        	if(clazz != null && Number.class.isAssignableFrom(clazz)) {
        		if(clazz == byte.class || clazz == Byte.class) {
        			return Byte.parseByte(valueStr);
        		} else if(clazz == int.class || clazz == Integer.class) {
        			return Integer.parseInt(valueStr);
        		} else if(clazz == float.class || clazz == Float.class) {
        			return Float.parseFloat(valueStr);
        		} else if(clazz == long.class || clazz == Long.class) {
        			return Long.parseLong(valueStr);
        		} else if(clazz == double.class || clazz == Double.class) {
        			return Double.parseDouble(valueStr);
        		} 
        		return Integer.parseInt(valueStr);
        	} else {
        		Object value = 0;
        		if(valueStr.length() > 9 ) {
        			value = Long.parseLong(valueStr) ;
        		} else {
        			value = Integer.parseInt(valueStr);
        		}
        		return value;
        	}
        } else if(isNumber(valueStr, true)) {
        	if(clazz != null && Number.class.isAssignableFrom(clazz)) {
        		if(clazz == byte.class || clazz == Byte.class) {
        			return Byte.parseByte(valueStr);
        		} else if(clazz == int.class || clazz == Integer.class) {
        			return Integer.parseInt(valueStr);
        		} else if(clazz == float.class || clazz == Float.class) {
        			return Float.parseFloat(valueStr);
        		} else if(clazz == long.class || clazz == Long.class) {
        			return Long.parseLong(valueStr);
        		} else if(clazz == double.class || clazz == Double.class) {
        			return Double.parseDouble(valueStr);
        		} 
        		return Integer.parseInt(valueStr);
        	} 
        	return Double.parseDouble(valueStr);
        } else if(valueStr.equals("null")) {
        	return null;
        } else if(valueStr.equals("true") || valueStr.equals("false")) {
        	return Boolean.parseBoolean(valueStr);
        } 
		return valueStr;
	}
	
	public static <T> Object parse(String json,Class<T> clazz) {
		
		if(json == null) 
			return null;
		try {
			return parseJson(json,clazz);
		} catch(Exception ex) {
		     throw new JsonException("parse json error ！",ex);	
		}
	}
	
	private static <T> Object parseJson(String json, Class<T> clazz) throws Exception {
		
		json = json.trim();
		
		// linkMap记下字符串中每个{和}的位置，{值计为1，}计为-1
	    // 大括号值设置为1和-1
		Map<Integer, Integer> map1 = new LinkedHashMap<Integer, Integer>();
		// 通用
		Map<Integer, Integer> map4 = new LinkedHashMap<Integer, Integer>();
		
		boolean isArray = false;
	
		if(json.startsWith("{") && json.endsWith("}")) {
			
		} else if(json.startsWith("[") && json.endsWith("]")) {
			isArray = true;
		}  else {
			throw new JsonException("JSON conversion exception :  json format error !");
		}
		json = json.substring(1, json.length() - 1) + ",";
		
	    int len = json.length();
	    for(int index= 0 ; index < len ; index ++) {
	    	char c = json.charAt(index);
	    	if(c == '{') {
	    		map1.put(index, 1);
	    		map4.put(index, 1);
	    	} else if(c == '}'){
	    		map1.put(index, -1);
	    		map4.put(index, -1);
	    	} else if(c == '[') {
	    		map4.put(index, 1);
	    	} else if(c == ']'){
	    		map4.put(index, -1);
	    	} else if(c == ',') {
	    		map4.put(index, 2);
	    	}
	    }
	    // 校验json格式正确性
		if(!isArray) {
			// 对象
			// {\"children\":[{\"name\":\"a1\"},{\"name\":\"a2\"}],\"name\":\"a\"}
			// 找,判断一个属性结束，标志：{+} && [+] = 0
			Class defaultClazz = LinkedHashMap.class;
			if(clazz == null) {
				clazz = defaultClazz;
			} 
			boolean isMap = Map.class.isAssignableFrom(clazz);
			if(isMap || clazz.isInterface()) {
				clazz = defaultClazz;
			}
			Object instance = clazz.newInstance();
			
			int total = 0;
	    	int beginIndex = 0;
	    	int endIndex = -1;
	    	
	    	boolean findPropertySplit = false;
	    	
    		for(Integer key : map4.keySet()) {
    			Integer value = map4.get(key);
    			if(value == 2) {
    				endIndex = key;
    				if(total == 0 ) {
    					
    					findPropertySplit = true;
    					String properyValueStr = json.substring(beginIndex, endIndex);
    					
    					if(properyValueStr.length() == 0) {
    						// 空集
    						continue;
    					}
    					
    					if(!properyValueStr.contains(":")) {
    						throw new JsonException("JSON conversion exception :  json format error !" + properyValueStr);
    					}
    					int splitIndex = properyValueStr.indexOf(":");
    					String propertyName = properyValueStr.substring(0, splitIndex).trim();
    					String valueStr = properyValueStr.substring(splitIndex + 1);
    					
    					String propertyKey = parseProperty(propertyName);
    					if(isMap) {
    						Object propertyValue = parseValue(valueStr);
    						AsmInvoke.invoke(instance, clazz, "put", propertyKey,propertyValue);
    					} else {
    						MethodInfo setterMethod = JsonHelper.getSetterMethodInfo(clazz,propertyKey);
    						if(setterMethod != null) {
    							Class paramType = setterMethod.getParamType();
    							String setterMethodName = setterMethod.getMethodName();
    							Class implClass = paramType;
    							if(Collection.class.isAssignableFrom(paramType)) {
    								// 取范型的类
    								implClass = setterMethod.getParadigm();
    							}
    							Object settingValue = parseValue(valueStr,implClass);
    							AsmInvoke.invoke(instance, clazz, setterMethodName, settingValue);
    						}
    					}
    					beginIndex = endIndex + 1;
    				}
    			} else {
    				total += value;
    			}
    		}
    		
    		if(!findPropertySplit) {
    			int splitIndex = json.indexOf(":");
				String propertyName = json.substring(0, splitIndex).trim();
				String valueStr = json.substring(splitIndex + 1);
				
				String propertyKey = parseProperty(propertyName);
				if(isMap) {
					Object propertyValue = parseValue(valueStr);
					AsmInvoke.invoke(instance, clazz, "put", propertyKey,propertyValue);
				} else {
					MethodInfo setterMethod = JsonHelper.getSetterMethodInfo(clazz,propertyKey);
					if(setterMethod != null && !setterMethod.isIgnore()) {
						Class paramType = setterMethod.getParamType();
						String setterMethodName = setterMethod.getMethodName();
						Class implClass = paramType;
						if(Collection.class.isAssignableFrom(paramType)) {
							// 取范型的类
							implClass = setterMethod.getParadigm();
						}
						Object settingValue = parseValue(valueStr,implClass);
						AsmInvoke.invoke(instance, clazz, setterMethodName, settingValue);
					}
				}
    		}
			return instance;
			
		} else {
		    // 数组
			List<Object> list = new ArrayList<Object>();
		
	    	int total = 0;
	    	int fromIndex = -1;
	    	int endIndex = -1;
	    
	    	boolean flag = false;
	    	// 遍历linkMap时，值相加，直到=0即匹配到	
	    	for(Integer key : map1.keySet()) {
	    		Integer value = map1.get(key);
	    		if(value == 1 && !flag) {
	    			fromIndex = key;
	    			flag = true;
	    		}
	    		if(value == -1) {
	    			endIndex = key;
	    		} 
	    		total += value;
	    		if(total == 0) {
	    			// 第一个元素串 {}
	    			String itemStr = json.substring(fromIndex, endIndex + 1);
	    			list.add(parse(itemStr,clazz));
	    			// 重置flag
	    			flag = false;
	    		}
	    	}
	    	
	    	return list;
		} 
	}

	public static Object parse(String json) {
		return parse(json, null);
	}
	
}
