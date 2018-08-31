package com.boco.mis.opentrace.plugins;

import com.boco.mis.opentrace.data.ApmTraceCollect;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.utils.StackTraceUtils;

public abstract class ApmEntryTracePlugin extends ApmPlugin {

	@Override
	public void catchError(Exception ex,TraceNode traceNode) {
		GlobalTrace trace = InterceptorHelper.getTrace();
		if(trace != null) {
			trace.setErrorFlag(true);
			trace.setStackTrace(StackTraceUtils.getStackTrace(ex));
		}
	}

	@Override
	public void afterCall(boolean globalEntry,TraceNode traceNode,Object[] args) {
		
		// trace 入口
		GlobalTrace trace = InterceptorHelper.getTrace();
		if(globalEntry && trace != null) {
			
			doResponse(args,trace);
			long endTimeMillis = System.currentTimeMillis();
			trace.setEndTimeMillis(endTimeMillis);
			trace.setTimeMillis(endTimeMillis
					- trace.getBeginTimeMillis());
			System.out.println(" globalTrace node size : "
					+ trace.getTraceNodes().size());
			// 采集数据
			ApmTraceCollect.collect(trace);
			InterceptorHelper.setTrace(null);
		}
		getResult().resetFields();
	}

	public abstract void doResponse(Object[] args, GlobalTrace trace) ;
}
