package com.boco.mis.opentrace.data.client.model;

public class Trace {

	
	public Trace() {
		// TODO Auto-generated constructor stub
	}

	private String traceId;

	private String traceName;

	private int traceNodeCount;
	
	private long beginMillis;

	// 结束毫秒数
	private long endMillis;

	
	private String beginTime;
	
	
	private String endTime;
	
	// 耗时
	private long timeMillis;

	// 响应码 适用http等的请求
	private int resCode;

	// 错误标志
	private boolean errorFlag;

	// 异常输出信息
	
	private String stackTraceId;

	// 是否http请求 默认是
	private boolean http;
	
	// http组件 struts1/2 /springmvc /servlet 
	
	private String httpComponent;
	
	// 完整路径
	
	private String requestURL;
	
	// 资源名称
	
	private String requestURI;
	
	// 资源上下文 如servlet容器根目录为/
	private String appName;
	
	// 请求方式
	private String httpMethod;
	
	// 参数信息
	private String queryString;
	
	private int httpStatus;
	
	private int serverCount; 
	
	
	private String xRequestedWith;
	
	
	private String userAgent;
	
	
	private String referer;
	
	
	private String jsessionid;
	
	
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

	// 所属应用服务器 {ip}:{port}
	private String server;
		
	// 应用服务器类型
	private String serverType;
	
	// 应用服务器版本
	private String serverVersion;

	// 是否进行转发
	private boolean forward;
	
	// 同contextPath下转发  资源名称requestURI集合，可能多次转发
	private String forwardURIs;
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public boolean isForward() {
		return forward;
	}

	public void setForward(boolean forward) {
		this.forward = forward;
	}

	public String getForwardURIs() {
		return forwardURIs;
	}

	public void setForwardURIs(String forwardURIs) {
		this.forwardURIs = forwardURIs;
	}
	
}
