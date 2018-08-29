package com.boco.mis.opentrace.interceptors;

import java.lang.reflect.Method;

import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;

public class InterceptorHelper {

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
}
