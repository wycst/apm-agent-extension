package com.boco.mis.opentrace.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.mis.opentrace.data.client.ApmTraceHttpClient;
import com.boco.mis.opentrace.data.client.gzip.GZip;
import com.boco.mis.opentrace.data.client.model.ApmTraceInfo;
import com.boco.mis.opentrace.data.client.model.StackTrace;
import com.boco.mis.opentrace.data.client.model.Trace;
import com.boco.mis.opentrace.data.server.Database;
import com.boco.mis.opentrace.data.server.Server;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.utils.JsonUtils;

public class ApmTraceCollect {

	// 所有的trace列表
	private static List<GlobalTrace> traces = new ArrayList<GlobalTrace>();
		
	// 每次发送时间间隔
	private int timeInterval;
	
	// 每次发送trace个数
	private int traceCountPerSend;
	
	/**
	 * trace数据
	 * @param trace
	 */
	public static void collect(GlobalTrace trace) {
		
		trace.generateTraceTree();
		
		// 添加到临时数据
		OpenTraceTemp.addTrace(trace);
		
		ApmTraceInfo traceInfo = toApmTraceInfo(trace);
		String traceInfoJsonSource = JsonUtils.toJsonString(traceInfo);
		try {
//			System.out.println("=====原始长度 len ： " + traceInfoJsonSource.length());
//			String gzipJsonSource = GZip.compress(traceInfoJsonSource);
//			System.out.println("=====压缩后： " + gzipJsonSource);
//			System.out.println("=====压缩后 len： " + gzipJsonSource.length());
//			String encodeJsonSource = java.net.URLEncoder.encode(gzipJsonSource, "utf-8");
//			System.out.println("=====编码后压缩1： " + encodeJsonSource);
//			
//			encodeJsonSource = java.net.URLEncoder.encode(encodeJsonSource, "utf-8");
//			System.out.println("=====编码后压缩2： " + encodeJsonSource);
//			System.out.println("=====编码后压缩2 len ： " + encodeJsonSource.length());
//			
//			String decodeContent = java.net.URLDecoder.decode(encodeJsonSource, "UTF-8");
//			System.out.println("=====解码后压缩： " + decodeContent);
//			
//			String source = GZip.uncompress(decodeContent);
//			System.out.println("还原后：" + source);
//			
			// 是否使用gzip压缩
			// 暂时来一个发一个    
			Map<String,String> headers = new HashMap<String,String>();
			headers.put("Content-type", "application/json");
			ApmTraceHttpClient.httpPost(traceInfoJsonSource,headers);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static ApmTraceInfo toApmTraceInfo(GlobalTrace trace) {
		
		ApmTraceInfo traceInfo = new ApmTraceInfo();
		Trace apmTrace = new Trace();
		apmTrace.setTraceId(trace.getTraceId());
		apmTrace.setTraceName(trace.getTraceName());
		apmTrace.setAppName(trace.getAppName() == null ? "/" : trace.getAppName());
		apmTrace.setBeginMillis(trace.getBeginTimeMillis());
		apmTrace.setBeginTime(trace.getBeginTime());
		apmTrace.setEndMillis(trace.getEndTimeMillis());
		apmTrace.setEndTime(trace.getEndTime());
		apmTrace.setErrorFlag(trace.isErrorFlag());
		apmTrace.setHttp(trace.isHttp());
		apmTrace.setHttpComponent(trace.getHttpComponent());
		apmTrace.setHttpMethod(trace.getHttpMethod());
		apmTrace.setHttpStatus(trace.getHttpStatus());
		apmTrace.setJsessionid(trace.getJsessionid());
		apmTrace.setQueryString(trace.getQueryString());
		apmTrace.setReferer(trace.getReferer());
		apmTrace.setRequestURI(trace.getRequestURI());
		apmTrace.setRequestURL(trace.getRequestURL());
		apmTrace.setResCode(trace.getResCode());
		apmTrace.setResponseServer(trace.getResponseServer());
		
		apmTrace.setStackTraceId(trace.getTraceId());
		apmTrace.setTimeMillis(trace.getTimeMillis());
		apmTrace.setUserAgent(trace.getUserAgent());
		apmTrace.setxRequestedWith(trace.getxRequestedWith());
		
		List<Server> servers = trace.getServers(); 
		List<TraceNode> traceNodes = trace.getTraceNodes() ;
		apmTrace.setTraceNodeCount(traceNodes == null ? 0 : traceNodes.size());
		apmTrace.setServerCount(servers == null ? 0 : servers.size());		
		
		// 1 trace
		traceInfo.setTrace(apmTrace);
		
		List<StackTrace> stackTraces = new ArrayList<StackTrace>();
		
		if(trace.isErrorFlag()) {
			StackTrace stackTrace = new StackTrace();
			stackTrace.setId(apmTrace.getStackTraceId());
			stackTrace.setText(trace.getStackTrace());
			stackTrace.setRef("traces");
			stackTraces.add(stackTrace);
		}
		
		if(apmTrace.getTraceNodeCount() > 0) {
			
			List<com.boco.mis.opentrace.data.client.model.TraceNode> apmTraceNodes = new ArrayList<com.boco.mis.opentrace.data.client.model.TraceNode>();
			
			for(TraceNode traceNode : traceNodes) {
				com.boco.mis.opentrace.data.client.model.TraceNode apmTraceNode = new 
						com.boco.mis.opentrace.data.client.model.TraceNode();
				apmTraceNode.setId(traceNode.getId());
				apmTraceNode.setBeginTimeMillis(traceNode.getBeginTimeMillis());
				apmTraceNode.setChildrenKeys(traceNode.getChildrenKeys().toString());
				apmTraceNode.setClassName(traceNode.getClassName());
				apmTraceNode.setEndTimeMillis(traceNode.getEndTimeMillis());
				apmTraceNode.setError(traceNode.isError());
				apmTraceNode.setFullMethodName(traceNode.getFullMethodName());
				apmTraceNode.setKey(traceNode.getKey());
				apmTraceNode.setMethodName(traceNode.getMethodName());
				apmTraceNode.setModule(traceNode.getModule());
				apmTraceNode.setParentKey(traceNode.getParentKey());
				apmTraceNode.setStackTraceId(traceNode.getId());
				apmTraceNode.setTraceCommand(traceNode.getTraceCommand());
				apmTraceNode.setTraceId(traceNode.getTraceId());
				apmTraceNode.setTraceNodeName(traceNode.getTraceNodeName());
				apmTraceNode.setTraceType(traceNode.getTraceType());
				apmTraceNodes.add(apmTraceNode);
				
				if(traceNode.isError()) {
					StackTrace stackTrace = new StackTrace();
					stackTrace.setId(traceNode.getId());
					stackTrace.setText(traceNode.getStackTrace());
					stackTrace.setRef("trace_nodes");
					stackTraces.add(stackTrace);
				}
			}
			// 2 apmTraceNodes
			traceInfo.setTraceNodes(apmTraceNodes);
		}
		// 3 stackTraces
		traceInfo.setStackTraces(stackTraces);
		
		if(apmTrace.getServerCount() > 0) {
			List<com.boco.mis.opentrace.data.client.model.Server> apmServers = new ArrayList<com.boco.mis.opentrace.data.client.model.Server>();
			for(Server server : servers) {
				com.boco.mis.opentrace.data.client.model.Server apmServer = new com.boco.mis.opentrace.data.client.model.Server();
				
				apmServer.setBegin(apmTrace.getBeginMillis());
				apmServer.setEnd(apmTrace.getEndMillis());
				apmServer.setTraceId(apmTrace.getTraceId());
				
				apmServer.setCategory(server.getCategory());
				apmServer.setHost(server.getHost());
				apmServer.setHostPortPair(server.getHostPortPair());
				
				apmServer.setName(server.getName());
				apmServer.setPort(server.getPort());
				apmServer.setType(server.getType());
				
				apmServer.setUser(server.getUser());
				apmServer.setDatabase(server.getDatabase());
				
				if(server instanceof Database) {
					apmServer.setUrl(((Database) server).getUrl());
				}
				apmServer.setVersion(server.getVersion());
				apmServers.add(apmServer);
			}
			traceInfo.setServers(apmServers);
		}
		
		return traceInfo;
	}
	
}
