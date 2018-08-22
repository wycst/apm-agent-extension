package com.boco.mis.opentrace.data.client.model;

public class TraceNode {

	public TraceNode() {
	}

	private String id;

	private String traceId;

	private String key;

	private String traceNodeName;

	private String className;

	private String methodName;

	private String fullMethodName;

	private long beginTimeMillis;

	private long endTimeMillis;

	private boolean error;

	private String stackTraceId;

	private String module;

	private String traceType;

	private String traceCommand;

	private String childrenKeys;

	private String parentKey;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTraceNodeName() {
		return traceNodeName;
	}

	public void setTraceNodeName(String traceNodeName) {
		this.traceNodeName = traceNodeName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getFullMethodName() {
		return fullMethodName;
	}

	public void setFullMethodName(String fullMethodName) {
		this.fullMethodName = fullMethodName;
	}

	public long getBeginTimeMillis() {
		return beginTimeMillis;
	}

	public void setBeginTimeMillis(long beginTimeMillis) {
		this.beginTimeMillis = beginTimeMillis;
	}

	public long getEndTimeMillis() {
		return endTimeMillis;
	}

	public void setEndTimeMillis(long endTimeMillis) {
		this.endTimeMillis = endTimeMillis;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getStackTraceId() {
		return stackTraceId;
	}

	public void setStackTraceId(String stackTraceId) {
		this.stackTraceId = stackTraceId;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTraceType() {
		return traceType;
	}

	public void setTraceType(String traceType) {
		this.traceType = traceType;
	}

	public String getTraceCommand() {
		return traceCommand;
	}

	public void setTraceCommand(String traceCommand) {
		this.traceCommand = traceCommand;
	}

	public String getChildrenKeys() {
		return childrenKeys;
	}

	public void setChildrenKeys(String childrenKeys) {
		this.childrenKeys = childrenKeys;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

}
