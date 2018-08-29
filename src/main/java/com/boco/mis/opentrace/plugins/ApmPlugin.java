package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;

public interface ApmPlugin {

	String getPluginName();
	
	String getEntryClass();
	
	String getInterceptMethod();
	
	String getTargetType();
	
	void doTrace(Method method, Callable<?> callable,
			 Object[] args, GlobalTrace globalTrace,
			TraceNode traceNode);
}
