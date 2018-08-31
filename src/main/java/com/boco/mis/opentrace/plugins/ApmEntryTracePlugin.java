package com.boco.mis.opentrace.plugins;

import com.boco.mis.opentrace.data.ApmTraceCollect;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.helper.InterceptorHelper;

public abstract class ApmEntryTracePlugin extends ApmPlugin {

	@Override
	public void catchError(Exception ex) {
		super.catchError(ex);
		
		
	}

	@Override
	public void afterCall() {
		super.afterCall();
		// trace 入口
		GlobalTrace trace = InterceptorHelper.getTrace();
		if(trace != null) {
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
	}
}
