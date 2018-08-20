package com.boco.mis.opentrace.json.asm;

public class MethodInfo {

	private String methodName;
	
	private Class paramType;
	
	private Class returnType;
	
	private int index;

	private boolean getter;
	
	private boolean setter;
	
	// 是否存在范型
	private Class paradigm;
	
	// 是否忽略序列
	private boolean ignore;

	public Class getParamType() {
		return paramType;
	}

	public void setParamType(Class paramType) {
		this.paramType = paramType;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Class getParadigm() {
		return paradigm;
	}

	public void setParadigm(Class paradigm) {
		this.paradigm = paradigm;
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class getReturnType() {
		return returnType;
	}

	public void setReturnType(Class returnType) {
		this.returnType = returnType;
	}

	public boolean isGetter() {
		return getter;
	}

	public void setGetter(boolean getter) {
		this.getter = getter;
	}

	public boolean isSetter() {
		return setter;
	}

	public void setSetter(boolean setter) {
		this.setter = setter;
	}
	
	
}
