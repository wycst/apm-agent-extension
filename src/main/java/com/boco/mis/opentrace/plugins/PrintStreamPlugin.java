package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.conf.ApmConfCenter;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.printstream.TracePrintStream;
/**
 * System.out.print/println
 * System.err.print/println
 * 
 * 
 * 
 * @author wangyunchao
 *
 */
public class PrintStreamPlugin extends ApmPlugin {

	private final String pluginName = "sysOut";

	private final String entryClass = "com.boco.mis.opentrace.printstream.TracePrintStream";

	private final String interceptMethod = "out";

	private final String targetType = "traceNode";

	private final static boolean onTracelog = "on".equals(ApmConfCenter.APM_TRACELOGGING); 

	static boolean hasTransformerTracePrintStream = false;
	
	static {
		if(onTracelog) {
			
		}
	}
	
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
		// 第一个参数是打印字符串（不为空），第2个参数是否换行
		String outStr = (String) args[0];
		boolean newLine = (Boolean) args[1];
		String printType = (String) args[2];
		globalTrace.tracelog(outStr);
		if(newLine) {
			globalTrace.tracelog("\n");
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
