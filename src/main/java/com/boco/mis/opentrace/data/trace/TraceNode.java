package com.boco.mis.opentrace.data.trace;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.boco.mis.opentrace.json.annotations.JsonIgnore;

/**
 * 单次服务调用记录
 * @author wangyunchao
 *
 * 2018年8月2日 下午5:34:42
 */
public class TraceNode {

	// id
	private String id;
	
	// 全局跟踪id由GlobalTrace生成
	private String traceId;
	
	// 标示唯一
	private String key;
	
	// 节点名称
	private String traceNodeName;
	
	// java类型方法调用类名称
	private String className;
	
	// java类型方法调用方法名称
	private String methodName;
	
	// 通过method的toString获取
	private String fullMethodName;
	
	private long beginTimeMillis;
	
	private long endTimeMillis;

	private boolean error;
	
	private String stackTrace;
	
	// 所属模块
	private String module;
	
	// 类型待归纳定义 java: /sql/redis/servlet/httpclient(向外)/spring mvc/struts/express/restful
	private String traceType;
	
	private String traceCommand;
	
    // 堆栈列表
	@JsonIgnore
	@JSONField(deserialize=false,serialize=false)
    private StackTraceElement[] stackTraces;
    
    private List<String> childrenKeys;
    
    private String parentKey;
    
    @JsonIgnore
    @JSONField(deserialize=false,serialize=false)
    private List<TraceNode> children;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
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

	public String getFullMethodName() {
		return fullMethodName;
	}

	public void setFullMethodName(String fullMethodName) {
//		
//		System.out.println(fullMethodName);
//		
		this.fullMethodName = fullMethodName;
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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@JsonIgnore
	public StackTraceElement[] getStackTraces() {
		return stackTraces;
	}

	@JsonIgnore
	public void setStackTraces(StackTraceElement[] stackTraces) {
		this.stackTraces = stackTraces;
	}

	public List<String> getChildrenKeys() {
		if(childrenKeys == null) {
			childrenKeys = new ArrayList<String>();
		}
		return childrenKeys;
	}

	public void setChildrenKeys(List<String> childrenKeys) {
		this.childrenKeys = childrenKeys;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getParentKey() {
		return parentKey;
	}

	@JsonIgnore
	public List<TraceNode> getChildren() {
		if(children == null) {
			children = new ArrayList<TraceNode>();
		}
		return children;
	}
	
	@JsonIgnore
	public void setChildren(List<TraceNode> children) {
		this.children = children;
	}
	
}
