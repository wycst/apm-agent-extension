package com.boco.mis.opentrace.json.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.boco.mis.opentrace.asm.com.esotericsoftware.reflectasm.MethodAccess;
import com.boco.mis.opentrace.json.annotations.JsonIgnore;
import com.boco.mis.opentrace.json.asm.MethodInfo;
import com.boco.mis.opentrace.reflect.AsmInvoke;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JsonHelper {

	/**
	 * 获取所有无参数方法
	 * @param clazz
	 * @return
	 */
	public static List<String> getNoneParamMethodNames(Class<?> clazz) {
		MethodAccess access = AsmInvoke.getMethodAccess(clazz);
		Class[][] parameterTypes = access.getParameterTypes();
		String[] methodNames = access.getMethodNames();
		List<String> noneParamMethodNames = new ArrayList<String>();
		int len = parameterTypes.length;
		for (int index = 0; index < len; index++) {
			if (parameterTypes[index].length == 0) {
				noneParamMethodNames.add(methodNames[index]);
			}
		}

		return noneParamMethodNames;
	}

	/**
	 * 获取setter方法中参数的范型类型 eg：T of List<T>
	 * @param clazz
	 * @param methodName
	 * @param paramType
	 * @return
	 * @throws Exception
	 */
	public static Class getParadigmClass(Class clazz ,String methodName,Class paramType) throws Exception {
		Method method = clazz.getMethod(methodName, paramType);
		Type genericType = method.getGenericParameterTypes()[0];
		if(genericType instanceof ParameterizedType){   
			ParameterizedType pt = (ParameterizedType) genericType;
			//得到泛型里的class类型对象  
			Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0]; 
			return genericClazz;
		}  
		return null;
	}
	
	/**
	 * 获取setter方法信息
	 * @param clazz
	 * @param propertyKey
	 * @return
	 */
	public static <T> MethodInfo getSetterMethodInfo(Class<T> clazz, String propertyKey) {
		MethodAccess access = AsmInvoke.getMethodAccess(clazz);
		String setterMethodName = "set" + propertyKey.substring(0, 1).toUpperCase() + propertyKey.substring(1);

		String[] methodNames = access.getMethodNames();
		Class[][] parameterTypes = access.getParameterTypes();

		Class parameterType = null;
		int setterIndex = -1;
		for (int i = 0; i < methodNames.length; i++) {
			String methodName = methodNames[i];
			if (methodName.equals(setterMethodName) && parameterTypes[i].length == 1) {
				parameterType = parameterTypes[i][0];
				setterIndex = i;
				break;
			}
		}
		if (parameterType != null) {
			// 参数个数为1
			MethodInfo info = new MethodInfo();
			info.setMethodName(setterMethodName);
			info.setParamType(parameterType);
			info.setIndex(setterIndex);
			info.setSetter(true);
			try {
				Method method = clazz.getMethod(setterMethodName,parameterType);
				info.setIgnore(method.getAnnotation(JsonIgnore.class) != null);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			if(Collection.class.isAssignableFrom(parameterType)) {
				try {
					// 如果是集合类型获取范型
					info.setParadigm(getParadigmClass(clazz,setterMethodName,parameterType));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return info;
		}

		return null;
	}

	/**
	 * 获取getter信息
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static MethodInfo getGetterMethodInfo(Class<?> clazz, String methodName) {
		try {
			MethodInfo methodInfo = new MethodInfo();
			
			Method getterMethod = clazz.getMethod(methodName);
			Class<?> returnType = getterMethod.getReturnType();
			methodInfo.setGetter(true);
			if(methodName.startsWith("is") && returnType != boolean.class) {
				methodInfo.setGetter(false);
			}
			JsonIgnore isonIgnore = getterMethod.getAnnotation(JsonIgnore.class);
			if(isonIgnore != null) {
				methodInfo.setIgnore(true);
			}
			methodInfo.setMethodName(methodName);
			return methodInfo;
		} catch(Exception ex) {
			
		}
		return null;
	}
	
	
}
