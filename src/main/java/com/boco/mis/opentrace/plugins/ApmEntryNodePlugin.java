package com.boco.mis.opentrace.plugins;

import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.utils.StackTraceUtils;


public abstract class ApmEntryNodePlugin extends ApmPlugin {

	@Override
	boolean entry() {
		return InterceptorHelper.getTrace() != null;
	}

	@Override
	public void catchError(Exception ex,TraceNode traceNode) {
		if(traceNode != null) {
			traceNode.setError(true);
			traceNode.setStackTrace(StackTraceUtils.getStackTrace(ex));
		}
	}

	@Override
	public void afterCall(boolean globalEntry,TraceNode traceNode,Object[] args) {
		getResult().resetFields();
	}
	
}
