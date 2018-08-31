package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.reflect.AsmInvoke;
/**
 * HttpServlet 插件 
 * 
 * 继承ApmEntryTracePlugin的考虑因素：
 *    Servlet运行容器如weblogic插件不存在时，也可以跟踪到部分Servlet API
 * 
 * 
 * @author wangyunchao
 *
 * 2018年8月31日 下午4:17:50
 */
public class HttpServletPlugin extends ApmEntryTracePlugin {

	private final String pluginName = "Servlet";

	private final String entryClass = "javax.servlet.http.HttpServlet";

	private final String interceptMethod = "service";

	private final String targetType = "trace";

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
		// HttpServlet
		Object request = args[0];
		// org.apache.catalina.core.ApplicationHttpRequest
		String requestURI = AsmInvoke.invoke(request, request.getClass(),
				"getRequestURI") + "";
		if (requestURI.toLowerCase().matches(ignoreRequestPattern)) {
			// 静态资源
		} else {
			boolean globalEntry = false;
			if (globalTrace == null) {
				globalTrace = new GlobalTrace();
				getResult().setGlobalEntry(globalEntry = true);
				InterceptorHelper.setTrace(globalTrace);
			}
			
			// 如果HttpServlet是trace的入口
			if(globalEntry) {
				if (!"Servlet".equals(globalTrace.getHttpComponent())) {
					globalTrace.setHttpComponent("Servlet");
					globalTrace.setHttp(true);
					globalTrace.setHttpMethod(AsmInvoke.invoke(request,
							request.getClass(), "getMethod")
							+ "");
					globalTrace.setQueryString(AsmInvoke.invoke(request,
							request.getClass(), "getQueryString")
							+ "");
					globalTrace.setRequestURI(requestURI);
					globalTrace.setTraceName(requestURI);
					globalTrace.setRequestURL(AsmInvoke.invoke(request,
							request.getClass(), "getRequestURL")
							+ "");
				}
			}
			
			// 设置appName
			if (globalTrace.getAppName() == null) {
				String contextPath = AsmInvoke.invoke(request,
						request.getClass(), "getContextPath") + "";
				if (contextPath.length() == 0) {
					contextPath = "/";
				}
				globalTrace.setAppName(contextPath.matches("\\/.+") ? contextPath
						.substring(1) : contextPath);
			}
			
		}
	}

	@Override
	public void doResponse(Object[] args,GlobalTrace trace) {
		
	}

}
