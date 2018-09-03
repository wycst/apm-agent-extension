package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.conf.ApmConfCenter;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
/**
 * System.out.print/println
 * System.err.print/println
 * 
 * 
 * 
 * @author wangyunchao
 *
 */
public class LogPlugin extends ApmPlugin {

	private final String pluginName = "log";

	private final String entryClass = "java.io.PrintStream";

	private final String interceptMethod = "print(ln)?";

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
		// TODO Auto-generated method stub
		String methodName = method.getName();
		if(methodName.matches(interceptMethod)) {
			return true;
		}
		return false;
	}

	@Override
	void doTrace(Method method, Callable<?> callable, Object[] args, GlobalTrace globalTrace, TraceNode traceNode)
			throws Exception {
		String methodName = method.getName();
		Object log = args.length == 0 ? "" : args[0];
		if(methodName.equals("println")) {
			globalTrace.tracelog(log + "\n");
		} else {
			globalTrace.tracelog(log);
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
