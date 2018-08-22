package com.boco.mis.opentrace.data.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 
 * @author wangyunchao
 *
 * 2018年8月22日 下午2:33:23
 */
public class ApmTraceHttpClient {

	static String url = "http://localhost:8085";
	
	static String testEndPoint = "/connect"; 
	
	static String endPoint = "/trace"; 
	
	private static boolean connect = true;
	
	// 一系列配置
	static {
		// 读取配置文件初始化url
	}
	
	public static void httpPost(String data)
			throws UnsupportedEncodingException, IOException {
		httpPost(data, null);
	}
	
	private static boolean connectable() {
		try {
			URLConnection conn = new URL(url + testEndPoint).openConnection();
			if(conn instanceof HttpURLConnection) {
				HttpURLConnection httpConn = (HttpURLConnection) conn;
				httpConn.setRequestMethod("HEAD");
				if(HttpURLConnection.HTTP_OK == httpConn.getResponseCode()){
					return true;
				}
			}
		} catch (Exception e) {
		    // 
			e.printStackTrace();
		}
		return connect = false;
	}
	

	public static void httpPost(String data,
			Map<String, String> header) {
		
		System.out.println(" httpPost ...");
		if(!connect || !connectable()) {
			return ;
		}
		
		PrintWriter out = null;
		BufferedReader in = null;
		URLConnection conn ;
		try {
			URL realUrl = new URL(url + endPoint);
			// 打开和URL之间的连接
			conn = realUrl.openConnection();
			// 设置超时时间
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(15000);
			String result = "";
			
			// 设置通用的请求属性
			if (header != null) {
				for (Entry<String, String> entry : header.entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			// 输出流
			out = new PrintWriter(conn.getOutputStream());
			// 数据格式  k1=value1&k2=value2格式
			out.print(data);
			// flush输出流的缓冲
			out.flush();
			
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),
					"utf8"));
			String line = null;
			while ((line = in.readLine()) != null) {
			}
			if (in != null) {
				in.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		httpPost("uuuu=2222");
	}
	
}