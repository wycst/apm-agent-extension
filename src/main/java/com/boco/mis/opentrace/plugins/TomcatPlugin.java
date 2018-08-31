package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.reflect.AsmInvoke;

public class TomcatPlugin extends ApmEntryTracePlugin {

	private final String pluginName = "tomcat";

	private final String entryClass = "org.apache.catalina.connector.CoyoteAdapter";

	private final String interceptMethod = "service";

	private final String targetType = "trace";

	private static String serverInfo;
	
	private static String ServerNumber;
	
	public String getPluginName() {
		return pluginName;
	}

	public String getEntryClass() {
		return entryClass;
	}

	public String getInterceptMethod() {
		return interceptMethod;
	}

	public String getTargetType() {
		return targetType;
	}

	@Override
	public void doTrace(Method method, Callable<?> callable, Object[] args,
			GlobalTrace globalTrace, TraceNode traceNode) {
		
		try {
			Object request = args[0];
			// 获取资源路径
			String requestURI = AsmInvoke.invoke(request,
					request.getClass(), "requestURI")
					+ "";

			if (requestURI.toLowerCase().matches(ignoreRequestPattern)) {
				// 忽略静态资源请求
				InterceptorHelper.setTrace(null);
			} else {
				globalTrace = new GlobalTrace();
				getResult().setGlobalEntry(true);
				globalTrace.setHttpMethod(AsmInvoke.invoke(request,
						request.getClass(), "method")
						+ "");
				globalTrace.setQueryString(AsmInvoke.invoke(request,
						request.getClass(), "queryString") + "");
				globalTrace.setHttp(true);
				globalTrace.setTraceName(requestURI);
				globalTrace.setRequestURI(requestURI);

				String scheme = AsmInvoke.invoke(request,
						request.getClass(), "scheme")
						+ "";
				if (scheme.equals("null")) {
					scheme = "http";
				}
				String serverName = AsmInvoke.invoke(request,
						request.getClass(), "serverName")
						+ "";
				String serverPort = AsmInvoke.invoke(request,
						request.getClass(), "getServerPort")
						+ "";
				
				String requestURL = scheme + "://" + serverName + ":"
						+ serverPort + requestURI;
				globalTrace.setRequestURL(requestURL);
				
				String xRequestedWith = (String) AsmInvoke.invoke(request,request.getClass(), "getHeader","X-Requested-With");
				String userAgent = (String) AsmInvoke.invoke(request,request.getClass(), "getHeader","User-Agent") ;
				String referer = (String) AsmInvoke.invoke(request,request.getClass(), "getHeader","Referer") ;
//				String cookies = AsmInvoke.invoke(request,request.getClass(), "getHeader","Cookie") + "";
				
				globalTrace.setxRequestedWith(xRequestedWith);
				globalTrace.setUserAgent(userAgent);
				globalTrace.setReferer(referer);
				
				// 获取server信息
				globalTrace.setServer(serverName + ":"	+ serverPort);
				try {
					Class<?> serverInfoClass = Thread.currentThread().getContextClassLoader().loadClass("org.apache.catalina.util.ServerInfo");
					
					if(serverInfo == null) {
						serverInfo = (String)AsmInvoke.invoke(null,serverInfoClass, "getServerInfo");
					}
					if(ServerNumber == null) {
						ServerNumber = (String) AsmInvoke.invoke(null,serverInfoClass, "getServerNumber");
					}
					globalTrace.setServerType(serverInfo);
					globalTrace.setServerVersion(ServerNumber);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				InterceptorHelper.setTrace(globalTrace);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doResponse(Object[] args,GlobalTrace trace) {
		Object response = args[1];
		int status = (Integer) AsmInvoke.invoke(response,
				response.getClass(), "getStatus");
		trace.setHttpStatus(status);
		
		Object mimeHeaders = AsmInvoke.invoke(response,response.getClass(), "getMimeHeaders");
		if(mimeHeaders != null) {
			String responseServer = (String) AsmInvoke.invoke(mimeHeaders,mimeHeaders.getClass(), "getHeader","Server");
			trace.setResponseServer(responseServer);
		}
		
	}

}
