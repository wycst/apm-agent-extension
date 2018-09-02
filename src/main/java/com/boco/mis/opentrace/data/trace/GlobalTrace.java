package com.boco.mis.opentrace.data.trace;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.boco.mis.opentrace.data.server.Server;
import com.boco.mis.opentrace.data.trace.tree.TraceTreeNode;
import com.boco.mis.opentrace.utils.ArrayUtils;

/**
 * 一个全局调用追踪链
 * 
 * @author wangyunchao
 * 
 *         2018年8月2日 下午5:13:23
 */
public class GlobalTrace {

	public GlobalTrace() {
		this.traceId = generateTraceId();
		this.traceNodes = new ArrayList<TraceNode>();
		this.servers = new ArrayList<Server>();
		setBeginTimeMillis(System.currentTimeMillis());
	}

	// 编号自动生成
	private String traceId;

	// 名称 if http traceName => requestURI
	private String traceName;

	// 节点列表
	private List<TraceNode> traceNodes;
	
	private List<TraceTreeNode> traceTreeNodes;

	// 开始毫秒数
	private long beginTimeMillis;

	// 结束毫秒数
	private long endTimeMillis;

	// 开始时间
	private String beginTime;
	
	// 结束时间
	private String endTime;
	
	// 耗时
	private long timeMillis;

	// 响应码 适用http等的请求
	private int resCode;

	// 错误标志
	private boolean errorFlag;

	// 异常输出信息
	private String stackTrace;

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
	
	// http响应码
	private int httpStatus;
	
	private String server;
	
	// 应用服务器类型
	private String serverType;
	
	// 应用服务器版本
	private String serverVersion;
	
	// 第三方服务
	private List<Server> servers; 
	
	// 入口位置
	private int entryStackTraceIndex;
	
	// X-Requested-With
	private String xRequestedWith;
	// User-Agent
	private String userAgent;
	// 来源
	private String referer;
	// Cookie [JSESSIONID]
	private String jsessionid;
	// 来源ResponseHeader ： Server:Apache-Coyote/1.1
	private String responseServer;
	
	// 是否进行转发
	private boolean forward;
	
	// 同contextPath下转发  资源名称requestURI集合，可能多次转发
	private String forwardURIs;
	
	// 当前trace日志输出
	private StringBuffer traceLog = new StringBuffer();
	
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

//	public TraceNode getStartTraceNode() {
//		return startTraceNode;
//	}
//
//	public void setStartTraceNode(TraceNode startTraceNode) {
//		this.startTraceNode = startTraceNode;
//	}
//
//	public TraceNode getEndTraceNode() {
//		return endTraceNode;
//	}
//
//	public void setEndTraceNode(TraceNode endTraceNode) {
//		this.endTraceNode = endTraceNode;
//	}

	public List<TraceNode> getTraceNodes() {
		return traceNodes;
	}

	public long getBeginTimeMillis() {
		return beginTimeMillis;
	}

	public void setBeginTimeMillis(long beginTimeMillis) {
		this.beginTimeMillis = beginTimeMillis;
		this.setBeginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(beginTimeMillis)));
	}

	public long getEndTimeMillis() {
		return endTimeMillis;
	}

	public void setEndTimeMillis(long endTimeMillis) {
		this.endTimeMillis = endTimeMillis;
		this.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(endTimeMillis)));
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

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public boolean isHttp() {
		return http;
	}

	public void setHttp(boolean http) {
		this.http = http;
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

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getHttpComponent() {
		return httpComponent;
	}

	public void setHttpComponent(String httpComponent) {
		this.httpComponent = httpComponent;
	}

	public List<Server> getServers() {
		return servers;
	}
	
	public int getEntryStackTraceIndex() {
		return entryStackTraceIndex;
	}

	public void setEntryStackTraceIndex(int entryStackTraceIndex) {
		this.entryStackTraceIndex = entryStackTraceIndex;
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

	public static String generateTraceId() {
		long threadId = Thread.currentThread().getId();
		long timestamp = System.currentTimeMillis();
		return timestamp + "." + threadId;
	}
	
	public synchronized String generateTraceNodeKey() {
		return traceNodes.size() + 1 + "";
	}
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
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

	public void tracelog(Object log) {
		traceLog.append(log);
	}
	
	public String getTraceLog() {
		return traceLog.toString();
	}
	
	private HashSet<String> hostPortPairs = new HashSet<String>();
	
	public void addServer(Server server) {
		String hostPortPair = server.getHostPortPair();
		if(hostPortPairs.add(hostPortPair)) {
			servers.add(server);
		}
	}
	
	public void destroy() {
		hostPortPairs.clear();
		servers.clear();
		traceNodes.clear();
		
	}

	public List<TraceTreeNode> getTraceTreeNodes() {
		return traceTreeNodes;
	}

	// 
	// 集合中第一个元素为根 未必是根？
	// 
	public void generateTraceTree() {
		List<TraceNode> traceNodes = getTraceNodes();
		int traceSize;
		if((traceSize = traceNodes.size()) == 0) {
			return ;
		}
		
		int i ,j;
		for(i = traceSize - 1; i > 0 ; i -- ) {
			TraceNode traceNode = traceNodes.get(i);
			for(j = i - 1; j >= 0 ; j -- ) {
				TraceNode prevTraceNode = traceNodes.get(j);
				boolean subset = ArrayUtils.subset(traceNode.getStackTraces(), prevTraceNode.getStackTraces(), false);
				if(subset) {
					if(traceNode.getStackTraces().length == prevTraceNode.getStackTraces().length) {
						// 同级别的方法如for循环调用同一个方法
					} else {
						traceNode.setParentKey(prevTraceNode.getKey());
						prevTraceNode.getChildrenKeys().add(0, traceNode.getKey());
						prevTraceNode.getChildren().add(0,traceNode);
						// 如果一旦匹配到调用关系，计算下一个
						break;
					}
				}
			}
		}
		
		traceTreeNodes = new ArrayList<TraceTreeNode>();
		for(TraceNode traceNode : traceNodes) {
			// 根节点
			if(traceNode.getParentKey() == null) {
				traceTreeNodes.add(new TraceTreeNode(traceNode));
			}
		}
		
	}


}
