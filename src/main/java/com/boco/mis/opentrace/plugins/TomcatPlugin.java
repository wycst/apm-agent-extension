package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;

public class TomcatPlugin implements ApmPlugin {

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEntryClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInterceptMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTargetType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doTrace(Method method, Callable<?> callable, Object[] args,
			GlobalTrace globalTrace, TraceNode traceNode) {
		// TODO Auto-generated method stub

	}

}
