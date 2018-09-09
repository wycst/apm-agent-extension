package com.boco.mis.opentrace.data.client.model;

import java.util.List;

public class ApmTraceInfo {

	private Trace trace;
	
	private List<TraceNode> traceNodes;
	
	private List<Server> servers;
	
	private List<StackTrace> stackTraces;

	private TraceLog traceLog;
	
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

	public TraceLog getTraceLog() {
		return traceLog;
	}

	public void setTraceLog(TraceLog traceLog) {
		this.traceLog = traceLog;
	}
	
	
}
