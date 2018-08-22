package com.boco.mis.persist.model;

import com.boco.mis.persist.elasticsearch.annotation.EsIndex;
import com.boco.mis.persist.elasticsearch.annotation.EsIndexField;

@EsIndex(indexName = TraceNode.INDEX_NAME, indexType = TraceNode.INDEX_TYPE)
public class TraceNode extends ApmModelIndex {

	static final String INDEX_NAME = "trace_nodes";
	static final String INDEX_TYPE = "default_node";
	
	public TraceNode() {
	}

	@EsIndexField(fieldName = "id", fieldType = "keyword")
	private String id;

	@EsIndexField(fieldName = "traceId", fieldType = "keyword")
	private String traceId;

	@EsIndexField(fieldName = "key", fieldType = "keyword")
	private String key;

	@EsIndexField(fieldType = "keyword")
	private String traceNodeName;

	@EsIndexField(fieldType = "keyword")
	private String className;

	@EsIndexField(fieldType = "keyword")
	private String methodName;

	@EsIndexField(fieldType = "keyword")
	private String fullMethodName;

	@EsIndexField(fieldType = "long")
	private long beginTimeMillis;

	@EsIndexField(fieldType = "long")
	private long endTimeMillis;

	@EsIndexField(fieldType = "boolean")
	private boolean error;

	@EsIndexField(fieldType = "keyword")
	private String stackTraceId;

	@EsIndexField(fieldType = "keyword")
	private String module;

	@EsIndexField(fieldType = "keyword")
	private String traceType;

	@EsIndexField(fieldType = "text")
	private String traceCommand;

	@EsIndexField(fieldType = "text")
	private String childrenKeys;

	@EsIndexField(fieldType = "keyword")
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

	public String indexName() {
		return INDEX_NAME;
	}

	public String indexType() {
		return INDEX_TYPE;
	}
	
	public String indexId() {
		return id;
	}
	
}
