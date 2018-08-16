package com.boco.mis.opentrace.data;

import java.util.ArrayList;
import java.util.List;

import com.boco.mis.opentrace.data.server.Server;
import com.boco.mis.opentrace.data.trace.GlobalTrace;

/**
 * apm服务单元<br>
 * 
 * @author wangyunchao
 * 
 *         2018年8月8日 下午5:39:53
 */
public class APMService {

	// 服务名称
	private String serviceName;

	// 应用名称
	private String appName;
	
	// 列表
	private List<GlobalTrace> traces;
	
	// 服务器
	private List<Server> servers;
	
	// 最快
	private long fastest;
	
	// 最慢
	private long slowest;
	
	// 总数
	private int numFound;
	
	// 错误个数
	private int errorNumFound;

	public APMService() {
		traces = new ArrayList<GlobalTrace>();
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<GlobalTrace> getTraces() {
		return traces;
	}

	public void setTraces(List<GlobalTrace> traces) {
		this.traces = traces;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	public long getFastest() {
		return fastest;
	}

	public void setFastest(long fastest) {
		this.fastest = fastest;
	}

	public long getSlowest() {
		return slowest;
	}

	public void setSlowest(long slowest) {
		this.slowest = slowest;
	}

	public int getNumFound() {
		return numFound;
	}

	public void setNumFound(int numFound) {
		this.numFound = numFound;
	}

	public int getErrorNumFound() {
		return errorNumFound;
	}

	public void setErrorNumFound(int errorNumFound) {
		this.errorNumFound = errorNumFound;
	}

	public void calculate() {
		
		this.numFound = traces.size();
		
		int errorNumFound = 0;
		long fastest = Long.MAX_VALUE;
		long slowest = 0;
		for(GlobalTrace trace : traces) {
		     if(trace.isErrorFlag()) {
		    	 errorNumFound ++;
		     }
		     fastest = Math.min(fastest, trace.getTimeMillis());
		     slowest = Math.max(slowest, trace.getTimeMillis());
		}
		this.fastest = fastest;
		this.slowest = slowest;
		this.errorNumFound = errorNumFound;
	}
	
}
