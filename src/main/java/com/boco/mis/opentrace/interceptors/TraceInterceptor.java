package com.boco.mis.opentrace.interceptors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import com.boco.mis.opentrace.data.server.Database;
import com.boco.mis.opentrace.data.server.Redis;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.reflect.AsmInvoke;
import com.boco.mis.opentrace.utils.StackTraceUtils;
import com.boco.mis.opentrace.data.ApmTraceCollect;

public class TraceInterceptor {

	private static ThreadLocal<GlobalTrace> globalTraceThreadLocal = new ThreadLocal<GlobalTrace>();

	private static String ignoreRequestPattern = ".*\\.(txt|js|css|gif|jpg|png|ico)";

	private static String ignoreTraceNodePattern = "(freemarker|(org.apache.struts2|com.opensymphony)|redis.clients|org.springframework|org.apache.catalina|java.(lang|io|util)|javax.servlet|com.mysql.jdbc).*";
	
	@RuntimeType
	public static Object intercept(@Origin Method method,
			@SuperCall Callable<?> callable, @AllArguments Object[] args)
			throws Exception {

		// 方法开始
		boolean isGlobalTrace = false;
		boolean isTomcatRequestResource = false;
		GlobalTrace globalTrace = globalTraceThreadLocal.get();

		// 判断是否为 tomcat的请求资源
		if (method.getDeclaringClass().getName()
				.equals("org.apache.catalina.connector.CoyoteAdapter")
				&& method.getName().equals("service")) {

			try {
				Object request = args[0];
				// 获取资源路径
				String requestURI = AsmInvoke.invoke(request,
						request.getClass(), "requestURI")
						+ "";

				if (requestURI.toLowerCase().matches(ignoreRequestPattern)) {
					// 忽略静态资源请求
					globalTraceThreadLocal.set(null);
				} else {
					isTomcatRequestResource = true;
					// 其他请求创建globalTrace入口
					isGlobalTrace = true;
					globalTrace = new GlobalTrace();
					globalTrace.setHttpMethod(AsmInvoke.invoke(request,
							request.getClass(), "method")
							+ "");
					globalTrace.setQueryString(AsmInvoke.invoke(request,
							request.getClass(), "queryString") + "");
					globalTrace.setHttp(true);
					globalTrace.setTraceName(requestURI);
					globalTrace.setRequestURI(requestURI);

					String scheme = AsmInvoke.invoke(request,
							request.getClass(), "scheme")
							+ "";
					if (scheme.equals("null")) {
						scheme = "http";
					}
					String serverName = AsmInvoke.invoke(request,
							request.getClass(), "serverName")
							+ "";
					String serverPort = AsmInvoke.invoke(request,
							request.getClass(), "getServerPort")
							+ "";

					String requestURL = scheme + "://" + serverName + ":"
							+ serverPort + requestURI;
					globalTrace.setRequestURL(requestURL);
					
					String xRequestedWith = (String) AsmInvoke.invoke(request,request.getClass(), "getHeader","X-Requested-With");
					String userAgent = (String) AsmInvoke.invoke(request,request.getClass(), "getHeader","User-Agent") ;
					String referer = (String) AsmInvoke.invoke(request,request.getClass(), "getHeader","Referer") ;
//					String cookies = AsmInvoke.invoke(request,request.getClass(), "getHeader","Cookie") + "";
					
					globalTrace.setxRequestedWith(xRequestedWith);
					globalTrace.setUserAgent(userAgent);
					globalTrace.setReferer(referer);
					
//					Object mimeHeaders = AsmInvoke.invoke(response,response.getClass(), "getMimeHeaders");
//					if(mimeHeaders != null) {
//						String responseServer = AsmInvoke.invoke(mimeHeaders,mimeHeaders.getClass(), "getHeader","Server") + "";
//					}
					
					globalTraceThreadLocal.set(globalTrace);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// HttpServlet
		if (method.getDeclaringClass().getName()
				.equals("javax.servlet.http.HttpServlet")
				&& method.getName().equals("service")) {
			Object request = args[0];
			String requestURI = AsmInvoke.invoke(request, request.getClass(),
					"getRequestURI") + "";
			if (requestURI.toLowerCase().matches(ignoreRequestPattern)) {
				// 静态资源
			} else {
				// service会被调用2次，一次是
				// 实现GenericServlet的public(参数servletRequest)，一次是内部定义的protected（参数httpservletrequest）
				if (globalTrace == null) {
					isGlobalTrace = true;
					globalTrace = new GlobalTrace();
					globalTraceThreadLocal.set(globalTrace);
				}
				if (!"Servlet".equals(globalTrace.getHttpComponent())) {
					globalTrace.setHttpComponent("Servlet");
					globalTrace.setHttp(true);
					globalTrace.setHttpMethod(AsmInvoke.invoke(request,
							request.getClass(), "getMethod")
							+ "");
					globalTrace.setQueryString(AsmInvoke.invoke(request,
							request.getClass(), "getQueryString") + "");
					globalTrace.setRequestURI(AsmInvoke.invoke(request,
							request.getClass(), "getRequestURI")
							+ "");
					globalTrace.setTraceName(globalTrace.getRequestURI());
					globalTrace.setRequestURL(AsmInvoke.invoke(request,
							request.getClass(), "getRequestURL")
							+ "");
				}
			}
		}

		TraceNode traceNode = new TraceNode();

		// 只有globalTrace存在的情况下进行跟踪记录
		if (globalTrace != null) {
			// 设置appName
			if (method.getDeclaringClass().getName()
					.equals("org.apache.catalina.core.ApplicationFilterChain")
					&& method.getName().equals("doFilter")) {
				if (globalTrace.getAppName() == null) {
					Object servletRequest = args[0];
					Map<String, Object> vars = new HashMap<String, Object>();
					vars.put("request", servletRequest);
					String contextPath = AsmInvoke.invoke(servletRequest,
							servletRequest.getClass(), "getContextPath") + "";
					if (contextPath.length() == 0) {
						contextPath = "/";
					}
					globalTrace
							.setAppName(contextPath.matches("\\/.+") ? contextPath
									.substring(1) : contextPath);
				}
			}
			// 设置httpComponent： spring mvc / struts /
			if (method.getDeclaringClass().getName()
					.equals("org.springframework.web.servlet.DispatcherServlet")
					&& method.getName().equals("doService")) {
				// spring mvc
				globalTrace.setHttpComponent("SpringMVC");
			} else if(method.getDeclaringClass().getName().equals("org.apache.struts2.dispatcher.Dispatcher") && method.getName().equals("serviceAction")) {
				globalTrace.setHttpComponent("Struts2");
				String appName = AsmInvoke.invoke(args[0], args[0].getClass(),
						"getContextPath") + "";
				if(appName.equals("")) {
					globalTrace.setAppName("/");
				} else {
					globalTrace.setAppName(appName.matches("\\/.+") ? appName
							.substring(1) : appName);
				}
			} else if(method.getDeclaringClass().getName()
					.equals("org.apache.jasper.servlet.JspServlet") 
					&& method.getName().equals("service")) {
				System.out.println("org.apache.jasper.servlet.JspServlet");
			}
			
			if (!method.getDeclaringClass().getName()
					.matches(ignoreTraceNodePattern)) {
				InterceptorHelper.recordTraceNode(traceNode, globalTrace, method);
			}
			
			/* mysql sql */
			if (method.getDeclaringClass().getName()
					.equals("com.mysql.jdbc.MysqlIO")
					&& method.getName().equals("sqlQueryDirect")) {
				if (args.length == 10) {
					Object statementImpl = args[0];
					String sql = (String) args[1];
					if(statementImpl != null) {
						InterceptorHelper.recordTraceNode(traceNode, globalTrace, method);
						traceNode.setModule("mysql");
						traceNode.setTraceType("sql");
						try {
							Object target = null;
							// callstatement
							if (statementImpl != null) {
								if(args[3] != null) {
									// queryPacket = args[3] 
									// statementImpl -> asSql 
									sql = (String) AsmInvoke.invoke(statementImpl,
											statementImpl.getClass(), "asSql");
								}
								traceNode.setTraceCommand(sql);
								target = AsmInvoke.invoke(statementImpl,
										statementImpl.getClass(), "getConnection");
								
								String database = (String) args[8];
								
								Field passwordField = target.getClass().getSuperclass()
										.getDeclaredField("password");
								passwordField.setAccessible(true);
								String password = passwordField.get(target) + "";
								
								Field portField = target.getClass().getSuperclass()
										.getDeclaredField("port");
								portField.setAccessible(true);
								String port = portField.get(target) + "";
								
								// 创建server
								Database dbServer = new Database();
								dbServer.setCategory("database");
								dbServer.setDatabase(database);
								dbServer.setType("mysql");
								dbServer.setHost(AsmInvoke.invoke(target,
										target.getClass(), "getHost")
										+ "");
								dbServer.setPort(port);
								dbServer.setUrl(AsmInvoke.invoke(target,
										target.getClass(), "getURL")
										+ "");
								dbServer.setUser(AsmInvoke.invoke(target,
										target.getClass(), "getUser")
										+ "");
								dbServer.setPassword(password);
								globalTrace.addServer(dbServer);
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
			} else if (method.getDeclaringClass().getName()
					.startsWith("redis.clients")) {
				// redis
				String methodName = method.getName();
				if (method.getDeclaringClass().getName()
						.equals("redis.clients.jedis.BinaryJedis")
						&& methodName.equals("connect")) {
					// 获取连接
					// recordTraceNode(traceNode,globalTrace,method);
					// traceNode.setModule("redis");
				} else if (method.getDeclaringClass().getName()
						.equals("redis.clients.jedis.Jedis") ) {
					if (args.length > 0) {
						InterceptorHelper.recordTraceNode(traceNode, globalTrace, method);
						traceNode.setModule("redis");
						try {
							Field argument0 = callable.getClass()
									.getDeclaredField("argument0");
							argument0.setAccessible(true);
							Object jedis = argument0.get(callable);

							Object jedisClient = AsmInvoke.invoke(jedis,
									jedis.getClass(), "getClient");

							String db = AsmInvoke.invoke(jedisClient,
									jedisClient.getClass(), "getDB") + "";
							
							String host = AsmInvoke.invoke(jedisClient,
									jedisClient.getClass(), "getHost") + "";
							String port = AsmInvoke.invoke(jedisClient,
									jedisClient.getClass(), "getPort") + "";

							traceNode.setTraceType("redis");
							
							Redis redis = new Redis();
							redis.setDatabase(db);
							redis.setHost(host);
							redis.setPort(port);
							redis.setCategory("database");
							redis.setType("redis");

							traceNode.setTraceCommand("host:" + redis.getHostPortPair() + "\n  db:" + db + "\n  " +  methodName + ":"
									+ Arrays.asList(args));
							
							globalTrace.addServer(redis);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}

		/* httpclient */
		if (method.getDeclaringClass().getName()
				.equals("org.apache.http.impl.client.CloseableHttpClient")
				&& method.getName().equals("execute")) {
			
			if (args.length == 2) {
				Map<String, Object> vars = new HashMap<String, Object>();
				vars.put("request", args[0]);
			}
		}
		
		if (traceNode.getFullMethodName() != null) {
			StackTraceElement[] stackTraces = Thread.currentThread()
					.getStackTrace();
			int to = stackTraces.length;
			traceNode.setStackTraces(Arrays.copyOfRange(stackTraces, 2, to));
		}
		try {
			// 函数执行
			return callable.call();
		} catch (Exception e) {
			if (globalTrace != null) {
				traceNode.setError(true);
				traceNode.setStackTrace(StackTraceUtils.getStackTrace(e));
				globalTrace.setErrorFlag(true);
				globalTrace.setStackTrace(traceNode.getStackTrace());
			}
			throw e;
		} finally {

			// 获取tomcat请求响应状态码
			if (isTomcatRequestResource) {
				Object response = args[1];
				int status = (Integer) AsmInvoke.invoke(response,
						response.getClass(), "getStatus");
				globalTrace.setHttpStatus(status);
			}
			// last doWith
			traceNode.setEndTimeMillis(System.currentTimeMillis());
			// 结束
			if (isGlobalTrace) {
				globalTrace.setEndTimeMillis(traceNode.getEndTimeMillis());
				globalTrace.setTimeMillis(globalTrace.getEndTimeMillis()
						- globalTrace.getBeginTimeMillis());
				System.out.println(" globalTrace node size : "
						+ globalTrace.getTraceNodes().size());
				// 采集数据
				ApmTraceCollect.collect(globalTrace);
				globalTraceThreadLocal.set(null);
			}
		}
	}


}
