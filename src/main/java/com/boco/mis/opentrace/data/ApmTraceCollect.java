package com.boco.mis.opentrace.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.boco.mis.opentrace.data.trace.GlobalTrace;
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
		OpenTraceTemp.addTrace(trace);
		String traceSourceData = JsonUtils.toJsonString(trace);
		
		System.out.println("原始数据:" + traceSourceData);
		try {
			String zipContent = ZipUtil.compress(traceSourceData);
			System.out.println("=====压缩后： " + zipContent);
			String encodeContent = java.net.URLEncoder.encode(zipContent, "utf-8");
			System.out.println("=====加密后压缩： " + encodeContent);
			String decodeContent = java.net.URLDecoder.decode(encodeContent, "UTF-8");
			System.out.println("=====解密后压缩： " + decodeContent);
			
			String source = ZipUtil.uncompress(decodeContent);
			System.out.println("还原后：" + source);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// 是否使用gzip压缩
		// 暂时来一个发一个    
		try {
			ApmTraceHttpClient.httpPost("traceSourceData=\"" + ZipUtil.compress(traceSourceData) + "\"");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
