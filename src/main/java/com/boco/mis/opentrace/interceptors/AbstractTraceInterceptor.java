package com.boco.mis.opentrace.interceptors;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.conf.ApmConfCenter;
import com.boco.mis.opentrace.data.ApmTraceCollect;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.plugins.ApmPlugin;
import com.boco.mis.opentrace.plugins.ApmPlugin.TraceResult;
import com.boco.mis.opentrace.utils.StackTraceUtils;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public abstract class AbstractTraceInterceptor {

	@RuntimeType
	public static Object intercept(@Origin Method method,
			@SuperCall Callable<?> callable, @AllArguments Object[] args)
			throws Exception {
		TraceResult result = InterceptorHelper.doIntercept(method, callable, args);
		TraceNode traceNode = result.getTraceNode();
		GlobalTrace globalTrace = InterceptorHelper.getTrace();
		
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
			if (globalTrace != null) {
				String errorStackTrace = StackTraceUtils.getStackTrace(ex);
				if(traceNode != null) {
					traceNode.setError(true);
					traceNode.setStackTrace(errorStackTrace);
				}
				globalTrace.setErrorFlag(true);
				globalTrace.setStackTrace(errorStackTrace);
			}
			throw ex;
		} finally {
			long endTimeMillis = System.currentTimeMillis();
			if(traceNode != null) {
				traceNode.setEndTimeMillis(endTimeMillis);
			}
			// trace 入口
			if(result.isGlobalEntry()) {
				globalTrace.setEndTimeMillis(endTimeMillis);
				globalTrace.setTimeMillis(endTimeMillis
						- globalTrace.getBeginTimeMillis());
				System.out.println(" globalTrace node size : "
						+ globalTrace.getTraceNodes().size());
				// 采集数据
				ApmTraceCollect.collect(globalTrace);
				InterceptorHelper.setTrace(null);
			}
		}
	}
		
}
