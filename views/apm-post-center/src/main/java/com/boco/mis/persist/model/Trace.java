package com.boco.mis.persist.model;

import com.boco.mis.persist.elasticsearch.annotation.EsIndex;
import com.boco.mis.persist.elasticsearch.annotation.EsIndexField;
@EsIndex(indexName = Trace.INDEX_NAME, indexType = Trace.INDEX_TYPE)
public class Trace extends ApmModelIndex {

	static final String INDEX_NAME = "traces";
	static final String INDEX_TYPE = "default_trace";
	
	public Trace() {
		// TODO Auto-generated constructor stub
	}

	@EsIndexField(fieldType = "keyword")
	private String traceId;

	@EsIndexField(fieldType = "keyword")
	private String traceName;

	@EsIndexField(fieldType = "integer")
	private int traceNodeCount;
	
	@EsIndexField(fieldType = "long")
	private long beginMillis;

	// 结束毫秒数
	@EsIndexField(fieldType = "long")
	private long endMillis;

	@EsIndexField(fieldType = "keyword")
	private String beginTime;
	
	@EsIndexField(fieldType = "keyword")
	private String endTime;
	
	// 耗时
	@EsIndexField(fieldType = "long")
	private long timeMillis;

	// 响应码 适用http等的请求
	@EsIndexField(fieldType = "integer")
	private int resCode;

	// 错误标志
	@EsIndexField(fieldType = "boolean")
	private boolean errorFlag;

	// 异常输出信息
	@EsIndexField(fieldType = "keyword")
	private String stackTraceId;

	// 是否http请求 默认是
	@EsIndexField(fieldType = "boolean")
	private boolean http;
	
	// http组件 struts1/2 /springmvc /servlet 
	@EsIndexField(fieldType = "keyword")
	private String httpComponent;
	
	// 完整路径
	@EsIndexField(fieldType = "keyword")
	private String requestURL;
	
	// 资源名称
	@EsIndexField(fieldType = "keyword")
	private String requestURI;
	
	// 资源上下文 如servlet容器根目录为/
	@EsIndexField(fieldType = "keyword")
	private String appName;
	
	// 请求方式
	@EsIndexField(fieldType = "keyword")
	private String httpMethod;
	
	// 参数信息
	@EsIndexField(fieldType = "keyword")
	private String queryString;
	
	@EsIndexField(fieldType = "integer")
	private int httpStatus;
	
	@EsIndexField(fieldType = "integer")
	private int serverCount; 
	
	@EsIndexField(fieldType = "keyword")
	private String xRequestedWith;
	
	@EsIndexField(fieldType = "keyword")
	private String userAgent;
	
	@EsIndexField(fieldType = "keyword")
	private String referer;
	
	@EsIndexField(fieldType = "keyword")
	private String jsessionid;
	
	@EsIndexField(fieldType = "keyword")
	private String responseServer;

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getTraceName() {
		return traceName;
	}

	public void setTraceName(String traceName) {
		this.traceName = traceName;
	}

	public int getTraceNodeCount() {
		return traceNodeCount;
	}

	public void setTraceNodeCount(int traceNodeCount) {
		this.traceNodeCount = traceNodeCount;
	}

	public long getBeginMillis() {
		return beginMillis;
	}

	public void setBeginMillis(long beginMillis) {
		this.beginMillis = beginMillis;
	}

	public long getEndMillis() {
		return endMillis;
	}

	public void setEndMillis(long endMillis) {
		this.endMillis = endMillis;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public long getTimeMillis() {
		return timeMillis;
	}

	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}

	public int getResCode() {
		return resCode;
	}

	public void setResCode(int resCode) {
		this.resCode = resCode;
	}

	public boolean isErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(boolean errorFlag) {
		this.errorFlag = errorFlag;
	}

	public String getStackTraceId() {
		return stackTraceId;
	}

	public void setStackTraceId(String stackTraceId) {
		this.stackTraceId = stackTraceId;
	}

	public boolean isHttp() {
		return http;
	}

	public void setHttp(boolean http) {
		this.http = http;
	}

	public String getHttpComponent() {
		return httpComponent;
	}

	public void setHttpComponent(String httpComponent) {
		this.httpComponent = httpComponent;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public int getServerCount() {
		return serverCount;
	}

	public void setServerCount(int serverCount) {
		this.serverCount = serverCount;
	}

	public String getxRequestedWith() {
		return xRequestedWith;
	}

	public void setxRequestedWith(String xRequestedWith) {
		this.xRequestedWith = xRequestedWith;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getJsessionid() {
		return jsessionid;
	}

	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}

	public String getResponseServer() {
		return responseServer;
	}

	public void setResponseServer(String responseServer) {
		this.responseServer = responseServer;
	}
	
	public String indexName() {
		return INDEX_NAME;
	}

	public String indexType() {
		return INDEX_TYPE;
	}
	
	public String indexId() {
		return traceId;
	}
}
