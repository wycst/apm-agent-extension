package com.boco.mis.test.main;

import com.boco.mis1.Trace1Service;

public final class TraceService {

	public void trace() {
		System.out.println("trace1");
		
		new Trace1Service().main(null);
		
	}
	
}
