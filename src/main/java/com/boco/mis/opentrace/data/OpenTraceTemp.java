package com.boco.mis.opentrace.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.boco.mis.opentrace.data.server.Server;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.utils.ObjectMapperUtils;

public class OpenTraceTemp {

	// 所有的trace列表
	private static List<GlobalTrace> traces = new ArrayList<GlobalTrace>();
	
	// 所有的server列表
	private static Map<String,com.boco.mis.opentrace.data.server.Server> servers = new HashMap<String,com.boco.mis.opentrace.data.server.Server>();
	
	private static Set<String> appNameSet = new HashSet<String>();
	
	public static void addTrace(GlobalTrace trace) {
		// 生成节点树
		trace.generateTraceTree();
		traces.add(trace);
		appNameSet.add(trace.getAppName());
		for(Server server : trace.getServers()) {
			addServer(server);
		}
	}
	
	public static void addServer(Server server) {
		servers.put(server.getHostPortPair(), server);
	}
	
	public static List<Map<String,Object>> getServers(final Map<String,Object> queryParams) {
		return ObjectMapperUtils.toBean(servers.values(), List.class);
	}
	
	public static List<Map<String,Object>> getTraces() {
		return ObjectMapperUtils.toBean(traces, List.class);
	}
	
	public static List<Map<String,Object>> queryApmService() {
		return queryApmService(null);
	}
	
	/**
	 * 获取应用名称集合，下拉选使用
	 * @return
	 */
	public static List<String> getAppNameOptions() {
		List<String> appNames = new ArrayList<String>(appNameSet);
		Collections.sort(appNames);
		return appNames;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getTraces(final Map<String,Object> queryParams) {
		
		List<GlobalTrace> cloneTraces = new ArrayList<GlobalTrace>(traces);
		cloneTraces.removeIf(new Predicate<GlobalTrace>() {
			
			public boolean test(GlobalTrace t) {
				return filter(t, queryParams);
			}
		});
		
		
		return ObjectMapperUtils.toBean(cloneTraces, List.class);
	}
	
	public static List<Map<String,Object>> queryApmService(Map<String,Object> queryParams) {
		
		// 对traces进行分组统计
		// 根据TraceName分组
		List<APMService> services = new ArrayList<APMService>();
		Map<String,APMService> serviceMap = new HashMap<String,APMService>();
		for(GlobalTrace trace : traces) {
			if(filter(trace,queryParams)) {
				continue;
			}
			String serviceName = trace.getTraceName();
			APMService apmService = serviceMap.get(serviceName);
			if(apmService == null) {
				apmService = new APMService();
				apmService.setServiceName(serviceName);
				apmService.setAppName(trace.getAppName());
				serviceMap.put(serviceName, apmService);
				services.add(apmService);
			}
			apmService.getTraces().add(trace);
		}
		
		for(APMService apmService : services) {
			apmService.calculate();
		}
		
		return ObjectMapperUtils.toBean(services, List.class);
	}
	
	private static boolean filter(GlobalTrace trace,
			Map<String, Object> queryParams) {
		
		if(queryParams == null) {
			return false;
		}
		
	    if(queryParams.containsKey("serviceName")) {
	    	if(!trace.getTraceName().contains(queryParams.get("serviceName") + "")) {
	    		return true;
	    	}
	    }
	    
	    if(queryParams.containsKey("appName")) {
	    	if(!"*".equals(queryParams.get("appName"))) {
	    		if(!trace.getAppName().equals(queryParams.get("appName") + "")) {
	    			return true;
	    		}
	    	}
	    }
	    
	    String beginTime = trace.getBeginTime();
	    if(queryParams.containsKey("beginTimeFrom")) {
	    	if(beginTime.compareTo(queryParams.get("beginTimeFrom") + "") < 0) {
	    		return true;
	    	}
	    }
	    if(queryParams.containsKey("beginTimeTo")) {
	    	if(beginTime.compareTo(queryParams.get("beginTimeTo") + "") > 0) {
	    		return true;
	    	}
	    }
		
		return false;
	}

	public static void clear() {
		traces.clear();
		servers.clear();
	}
}
