package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
/**
 * servlet 跳转处理拦截获取转发路径
 * @author wangyunchao
 *
 * 2018年8月31日 下午3:33:08
 */
public class ServletDispatcherPlugin extends ApmEntryNodePlugin {

	private final String pluginName = "ServletDispatcher";

	private final String entryClass = "org.apache.catalina.core.ApplicationDispatcher";

	private final String interceptMethod = "forward";

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

	@Override
	void doTrace(Method method, Callable<?> callable, Object[] args,
			GlobalTrace globalTrace, TraceNode traceNode) throws Exception {
		
		globalTrace.setForward(true);
		String forwardURIs = globalTrace.getForwardURIs();
		Object servletDispatcher = getEntryThis(callable);
		if(servletDispatcher != null) {
			Field requestURIField = servletDispatcher.getClass().getDeclaredField("requestURI");
			requestURIField.setAccessible(true);
			String forwardURI = (String) requestURIField.get(servletDispatcher);
			if(forwardURIs == null) {
				forwardURIs = forwardURI;
			} else {
				forwardURIs += " " + forwardURI;
			}
			globalTrace.setForwardURIs(forwardURIs);
		}
	}

}
