package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.conf.ApmConfCenter;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.utils.StackTraceUtils;

public class Log4jPlugin extends ApmPlugin {

	private final String pluginName = "log4j";

	private final String entryClass = "org.apache.log4j.Category";

	private final String interceptMethod = "forcedLog";

	private final String targetType = "traceNode";

	private final boolean onTracelog = "on".equals(ApmConfCenter.APM_TRACELOGGING); 
	
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
	boolean entry() {
		return onTracelog && InterceptorHelper.getTrace() != null;
	}

	@Override
	boolean entryMethod(Method method) {
		String methodName = method.getName();
		return methodName.equals(interceptMethod);
	}

	@Override
	void doTrace(Method method, Callable<?> callable, Object[] args, GlobalTrace globalTrace, TraceNode traceNode)
			throws Exception {
		String methodName = method.getName();
		
		System.out.println(method.getDeclaringClass().getName());
		System.out.println(getEntryThis(callable).getClass());
		
		if(args.length == 4) {
			Object log = args[2] == null ? "" : args[2].toString() ;
			// args String fqcn, Priority level, Object message, Throwable t
			globalTrace.tracelog(log + "\n");
			Throwable t = (Throwable) args[3];
			
			System.out.println(" args0 fqcn " + args[0]);
			System.out.println(" args1 level " + args[1]);
			
			if(t != null) {
				globalTrace.tracelog(StackTraceUtils.getStackTrace(t));
			}
			System.out.println("======== globalTrace tracelog " + globalTrace.getTraceLog());
		}	
	}

	@Override
	public void catchError(Exception ex, TraceNode traceNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCall(boolean globalEntry, TraceNode traceNode, Object[] args) {
		// TODO Auto-generated method stub
		
	}
}
