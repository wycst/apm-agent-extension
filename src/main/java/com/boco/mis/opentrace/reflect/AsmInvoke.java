package com.boco.mis.opentrace.reflect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.mis.opentrace.asm.com.esotericsoftware.reflectasm.FieldAccess;
import com.boco.mis.opentrace.asm.com.esotericsoftware.reflectasm.MethodAccess;

public class AsmInvoke {

	private static Map<Class<?>, MethodAccess> methodAccessCache = new HashMap<Class<?>, MethodAccess>();

	/**
	 * 缺陷：被调用方法本身是可变参数时无法调用
	 * 
	 * @param target
	 * @param clazz
	 * @param methodName
	 * @param paramTypes
	 * @param args
	 * @return
	 */
	public static Object invoke(Object target, Class<?> clazz, String methodName,
			Class[] paramTypes,Object... args) {
		MethodAccess access = getMethodAccess(clazz);
		if(paramTypes == null) {
			return access.invoke(target, methodName, args);
		} else {
			int index = access.getIndex(methodName, paramTypes);
			return access.invoke(target, index, args);
		}
	}
	
	private static MethodAccess getMethodAccess(Class<?> clazz) {
		MethodAccess access = methodAccessCache.get(clazz);
		if (access == null) {
			access = MethodAccess.get(clazz);
			methodAccessCache.put(clazz, access);
		}
		return access;
	}
	
	
	/**
	 * 缺陷：被调用方法本身是可变参数时无法调用
	 * 
	 * @param target
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @return
	 */
	public static Object invoke(Object target, Class<?> clazz, String methodName,
			Object... args) {
		return invoke(target,clazz,methodName,null,args);
	}
	
	
	public static List<String> getNoneParamMethodNames(Class<?> clazz) {
		MethodAccess access = getMethodAccess(clazz);
		Class[][] parameterTypes = access.getParameterTypes();
		String[] methodNames = access.getMethodNames();
		List<String> noneParamMethodNames = new ArrayList<String>();
		int len = parameterTypes.length;
		for(int index = 0 ; index < len ; index ++) {
		    if(parameterTypes[index].length == 0) {
		    	noneParamMethodNames.add(methodNames[index]);
		    }
		}
		
		return noneParamMethodNames;
	}
	
}