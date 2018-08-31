package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;

public class SpringMvcPlugin extends ApmEntryNodePlugin {

	private final String pluginName = "SpringMVC";

	private final String entryClass = "org.springframework.web.servlet.DispatcherServlet";

	private final String interceptMethod = "doService";

	private final String targetType = "traceNode";

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

	public void doTrace(Method method, Callable<?> callable, Object[] args,
			GlobalTrace globalTrace, TraceNode traceNode) {
		globalTrace.setHttpComponent(pluginName);
	}

}
