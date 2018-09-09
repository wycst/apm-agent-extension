package com.boco.mis.opentrace.interceptors;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.plugins.ApmPlugin;
import com.boco.mis.opentrace.plugins.ApmPlugin.TraceResult;
import com.boco.mis.opentrace.printstream.TracePrintStream;

public class NewTraceInterceptor {

	
	@RuntimeType
	public static Object intercept(@Origin Method method,
			@SuperCall Callable<?> callable, @AllArguments Object[] args)
			throws Exception {
		
		TraceResult result = InterceptorHelper.doIntercept(method, callable, args);
		TraceNode traceNode = result.getTraceNode();
		ApmPlugin plugin = result.getPlugin();
		boolean globalEntry = result.isGlobalEntry();
		
		if (traceNode != null && traceNode.getFullMethodName() != null) {
			StackTraceElement[] stackTraces = Thread.currentThread()
					.getStackTrace();
			int to = stackTraces.length;
			traceNode.setStackTraces(Arrays.copyOfRange(stackTraces, 2, to));
		}
		try {
			// 函数执行
			return callable.call();
		} catch(Exception ex) {
			if(plugin != null) {
				plugin.catchError(ex,traceNode);
			}
			throw ex;
		} finally {
			if(plugin != null) {
				plugin.afterCall(globalEntry,traceNode,args);
			}
			if(traceNode != null) {
				traceNode.setEndTimeMillis(System.currentTimeMillis());
			}
			result.resetFields();
		}
	}
	
}
