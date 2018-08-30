package com.boco.mis.opentrace.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.boco.mis.opentrace.data.server.Server;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.utils.JsonUtils;
/**
 * 测试临时数据 正式环境删除
 * @author wangyunchao
 *
 * 2018年8月30日 上午9:48:52
 */
public class OpenTraceTemp {

	// 所有的trace列表
	private static List<GlobalTrace> traces = new ArrayList<GlobalTrace>();
	
	// 所有的server列表
	private static Map<String,com.boco.mis.opentrace.data.server.Server> servers = new HashMap<String,com.boco.mis.opentrace.data.server.Server>();
	
	private static Set<String> appNameSet = new HashSet<String>();
	
	public static void addTrace(GlobalTrace trace) {
		// 生成节点树
		traces.add(trace);
		if(trace.getAppName() == null) {
			trace.setAppName("/");
		}
		appNameSet.add(trace.getAppName());
		for(Server server : trace.getServers()) {
			addServer(server);
		}
	}
	
	public static void addServer(Server server) {
		servers.put(server.getHostPortPair(), server);
	}
	
	public static String getServers(final Map<String,Object> queryParams) {
		return JsonUtils.toJsonString(servers.values());
	}
	
	public static String getTraces() {
		return JsonUtils.toJsonString(traces);
	}
	
	public static String queryApmService() {
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
	public static String getTraces(final Map<String,Object> queryParams) {
		
		// jdk 8 语法
//		List<GlobalTrace> cloneTraces = new ArrayList<GlobalTrace>(traces);
//		cloneTraces.removeIf(new java.util.function.Predicate<GlobalTrace>() {
//			
//			public boolean test(GlobalTrace t) {
//				return filter(t, queryParams);
//			}
//		});
		List<GlobalTrace> cloneTraces = new ArrayList<GlobalTrace>(); 
		for(GlobalTrace trace : traces) {
			if(!filter(trace, queryParams)) {
				cloneTraces.add(trace);
			}
		}
		
		return JsonUtils.toJsonString(cloneTraces);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String queryApmService(Map<String,Object> queryParams) {
		
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
		
		return JsonUtils.toJsonString(services);
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
