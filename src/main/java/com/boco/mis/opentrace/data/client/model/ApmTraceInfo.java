package com.boco.mis.opentrace.data.client.model;

import java.util.List;

public class ApmTraceInfo {

	private Trace trace;
	
	private List<TraceNode> traceNodes;
	
	private List<Server> servers;
	
	private List<StackTrace> stackTraces;

	public Trace getTrace() {
		return trace;
	}

	public void setTrace(Trace trace) {
		this.trace = trace;
	}

	public List<TraceNode> getTraceNodes() {
		return traceNodes;
	}

	public void setTraceNodes(List<TraceNode> traceNodes) {
		this.traceNodes = traceNodes;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	public List<StackTrace> getStackTraces() {
		return stackTraces;
	}

	public void setStackTraces(List<StackTrace> stackTraces) {
		this.stackTraces = stackTraces;
	}
	
}
