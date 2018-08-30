package com.boco.mis.opentrace.helper;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.conf.ApmConfCenter;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.plugins.ApmPlugin;
import com.boco.mis.opentrace.plugins.ApmPlugin.TraceResult;

public class InterceptorHelper {

	private final static ThreadLocal<GlobalTrace> globalTraceThreadLocal = new ThreadLocal<GlobalTrace>();

	public static GlobalTrace getTrace() {
		return globalTraceThreadLocal.get();
	}
	
	public static void setTrace(GlobalTrace trace) {
	    globalTraceThreadLocal.set(trace);
	}
	
	public static void recordTraceNode(TraceNode traceNode,
			GlobalTrace globalTrace, Method method) {
		String traceId = globalTrace.getTraceId();
		long beginTimeMillis = System.currentTimeMillis();
		String key = globalTrace.generateTraceNodeKey();
		traceNode.setKey(key);
		traceNode.setTraceId(traceId);
		traceNode.setClassName(method.getDeclaringClass().getName());
		traceNode.setBeginTimeMillis(beginTimeMillis);
		traceNode.setMethodName(method.getName());
		traceNode.setFullMethodName(method.toGenericString());
		traceNode.setId(traceId + "." + beginTimeMillis + "." + key);

		globalTrace.getTraceNodes().add(traceNode);

		if (globalTrace.getTraceNodes().size() == 1) {
			globalTrace.setEntryStackTraceIndex(Thread.currentThread()
					.getStackTrace().length - 4);
		}

		globalTrace.setErrorFlag(false);
		globalTrace.setStackTrace(null);
	}

	public static TraceResult doIntercept(Method method, Callable<?> callable,
			Object[] args) {
		TraceResult result  = null;
		List<ApmPlugin>  apmPlugins = ApmConfCenter.getPlugins();
		
		boolean pluginEntry = false;
		TraceNode traceNode = new TraceNode();
		for (ApmPlugin apmPlugin : apmPlugins) {
			GlobalTrace globalTrace = InterceptorHelper.getTrace();
			result = apmPlugin.trace(method, callable, args, globalTrace, traceNode);
			if(result.getTracePlugin() != null) {
				pluginEntry = true;
			}
			if(result.isTraced()) {
				
		    	break;
		    }
		}
		// 当前方法如果插件没有matcher到
		if(!pluginEntry) {
			GlobalTrace globalTrace = InterceptorHelper.getTrace();
			InterceptorHelper.recordTraceNode(traceNode, globalTrace, method);
			result.setTraceNode(traceNode);
		}
		
		return result;
	}
}
