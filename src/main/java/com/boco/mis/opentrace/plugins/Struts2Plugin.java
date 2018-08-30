package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.reflect.AsmInvoke;

public class Struts2Plugin extends ApmPlugin {

	private final String pluginName = "Struts2";

	private final String entryClass = "org.apache.struts2.dispatcher.Dispatcher";

	private final String interceptMethod = "serviceAction";

	private final String targetType = "trace_node";

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

		globalTrace.setHttpComponent("Struts2");
		String appName = AsmInvoke.invoke(args[0], args[0].getClass(),
				"getContextPath") + "";
		if (appName.equals("")) {
			globalTrace.setAppName("/");
		} else {
			globalTrace.setAppName(appName.matches("\\/.+") ? appName
					.substring(1) : appName);
		}
	}

}
