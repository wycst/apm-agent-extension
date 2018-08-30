package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.reflect.AsmInvoke;

public class HttpServletPlugin extends ApmPlugin {

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
		String requestURI = AsmInvoke.invoke(request, request.getClass(),
				"getRequestURI") + "";
		if (requestURI.toLowerCase().matches(ignoreRequestPattern)) {
			// 静态资源
		} else {
			if (globalTrace == null) {
				// isGlobalTrace = true;
				globalTrace = new GlobalTrace();
				getResult().setGlobalEntry(true);
				InterceptorHelper.setTrace(globalTrace);
			}
			if (!"Servlet".equals(globalTrace.getHttpComponent())) {
				globalTrace.setHttpComponent("Servlet");
				globalTrace.setHttp(true);
				globalTrace.setHttpMethod(AsmInvoke.invoke(request,
						request.getClass(), "getMethod")
						+ "");
				globalTrace.setQueryString(AsmInvoke.invoke(request,
						request.getClass(), "getQueryString")
						+ "");
				globalTrace.setRequestURI(AsmInvoke.invoke(request,
						request.getClass(), "getRequestURI")
						+ "");
				globalTrace.setTraceName(globalTrace.getRequestURI());
				globalTrace.setRequestURL(AsmInvoke.invoke(request,
						request.getClass(), "getRequestURL")
						+ "");
			}
		}
	}

}
