package com.boco.mis.opentrace.interceptors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

//import org.mvel2.MVEL;

import com.boco.mis.opentrace.data.OpenTraceTemp;
import com.boco.mis.opentrace.data.server.Database;
import com.boco.mis.opentrace.data.server.Redis;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.reflect.AsmInvoke;
import com.boco.mis.opentrace.utils.StackTraceUtils;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class TraceInterceptor {

	private static ThreadLocal<GlobalTrace> globalTraceThreadLocal = new ThreadLocal<GlobalTrace>();

	private static String ignoreRequestPattern = ".*\\.(txt|js|css|gif|jpg|png|ico)";

	private static String ignoreTraceNodePattern = "(freemarker|(org.apache.struts2|com.opensymphony)|redis.clients|org.apache.catalina|java.(lang|io|util)|org.springframework|javax.servlet|com.mysql.jdbc).*";

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
				Map<String, Object> vars = new HashMap<String, Object>();
				vars.put("request", request);
				// 获取资源路径
				// String requestURI = MVEL.eval("request.requestURI()", vars) +
				// "";
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

					// WebServer webServer = new WebServer();
					// webServer.setHost(serverName);
					// webServer.setPort(serverPort);
					// webServer.setCategory("WebServer");
					// webServer.setType("tomcat");
					//
					// globalTrace.addServer(webServer);

					String requestURL = scheme + "://" + serverName + ":"
							+ serverPort + requestURI;
					globalTrace.setRequestURL(requestURL);
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
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put("request", request);
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
			if (method
					.getDeclaringClass()
					.getName()
					.equals("org.springframework.web.servlet.DispatcherServlet")
					&& method.getName().equals("doService")) {
				// spring mvc
				globalTrace.setHttpComponent("SpringMVC");
			} /*else if (method.getDeclaringClass().getName()
					.matches("org.apache.struts2..*.StrutsActionProxy")
					&& method.getName().equals("execute")) {
				// StrutsPrepareAndExecuteFilter
				globalTrace.setHttpComponent("Struts2");
				// Object appName =
				// MVEL.eval("org.apache.struts2.ServletActionContext.getServletContext().getServletContextName()")
				// ;
				Object servletActionContext = AsmInvoke.invoke(null, Thread.currentThread().getContextClassLoader().loadClass("org.apache.struts2.ServletActionContext"),
						"getServletContext");
				
				System.out.println(" app name === " + servletActionContext.getClass().getMethod("getServletContextName").invoke(servletActionContext));
				
				Object appName = AsmInvoke.invoke(servletActionContext,
						servletActionContext.getClass(),
						"getServletContextName");
				globalTrace
						.setAppName(appName == null || appName.equals("") ? "/"
								: appName.toString());
			} */else if(method.getDeclaringClass().getName().equals("org.apache.struts2.dispatcher.Dispatcher") && method.getName().equals("serviceAction")) {
				globalTrace.setHttpComponent("Struts2");
				String appName = AsmInvoke.invoke(args[0], args[0].getClass(),
						"getContextPath") + "";
				if(appName.equals("")) {
					globalTrace.setAppName("/");
				} else {
					globalTrace.setAppName(appName.matches("\\/.+") ? appName
							.substring(1) : appName);
				}
			}
			

			if (!method.getDeclaringClass().getName()
					.matches(ignoreTraceNodePattern)) {
				recordTraceNode(traceNode, globalTrace, method);
			}

			// 下面分插件开发当前trace都在干什么事情
			/* mysql sql */
			if (method.getDeclaringClass().getName()
					.equals("com.mysql.jdbc.ConnectionImpl")
					&& method.getName().equals("execSQL")) {
				if (args.length == 10) {
					recordTraceNode(traceNode, globalTrace, method);
					Object sql = args[1];
					traceNode.setModule("mysql");
					traceNode.setTraceType("sql");
					try {
						Object target = null;
						// callstatement
						Object statementImpl = args[0];
						if (statementImpl != null) {

							if (sql == null) {
								sql = statementImpl.getClass()
										.getMethod("asSql")
										.invoke(statementImpl);
							}
							traceNode.setTraceCommand(sql + "");
							target = statementImpl.getClass()
									.getMethod("getConnection")
									.invoke(statementImpl);

							Map<String, Object> vars = new HashMap<String, Object>();
							vars.put("conn", target);
							Field passwordField = method.getDeclaringClass()
									.getDeclaredField("password");
							passwordField.setAccessible(true);
							String password = passwordField.get(target) + "";

							Field portField = method.getDeclaringClass()
									.getDeclaredField("port");
							portField.setAccessible(true);
							String port = portField.get(target) + "";

							// 创建server
							Database dbServer = new Database();
							dbServer.setCategory("database");
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
						.equals("redis.clients.jedis.Jedis") /*
															 * &&
															 * method.getName(
															 * ).equals
															 * ("sendCommand")
															 */) {
					if (args.length > 0) {
						recordTraceNode(traceNode, globalTrace, method);
						traceNode.setModule("redis");
						try {
							Field argument0 = callable.getClass()
									.getDeclaredField("argument0");
							argument0.setAccessible(true);
							Object jedis = argument0.get(callable);

							Map<String, Object> vars = new HashMap<String, Object>();
							vars.put("jedis", jedis);

							Object jedisClient = AsmInvoke.invoke(jedis,
									jedis.getClass(), "getClient");

							String host = AsmInvoke.invoke(jedisClient,
									jedisClient.getClass(), "getHost") + "";
							String port = AsmInvoke.invoke(jedisClient,
									jedisClient.getClass(), "getPort") + "";

							traceNode.setTraceType("redis");
							traceNode.setTraceCommand(methodName + ":"
									+ Arrays.asList(args));

							Redis redis = new Redis();
							redis.setHost(host);
							redis.setPort(port);
							redis.setCategory("database");
							redis.setType("redis");

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
			// System.out.println(" sql args :" + new
			// ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(args));
			if (args.length == 2) {
				Map<String, Object> vars = new HashMap<String, Object>();
				vars.put("request", args[0]);
				// System.out.println("httpclient url : " +
				// MVEL.eval("request.getURI()", vars));
			}
		}

		if (traceNode.getFullMethodName() != null) {
			StackTraceElement[] stackTraces = Thread.currentThread()
					.getStackTrace();
			// System.out.println("======== current method : " +
			// method.getDeclaringClass() + "." + method.getName());
			// for(StackTraceElement stackTrace : stackTraces) {
			// System.out.println("[" + stackTrace.getClassName() + "].[" +
			// stackTrace.getMethodName() + "]");
			// }
			// int entryStackTraceIndex = globalTrace.getEntryStackTraceIndex();
			int to = stackTraces.length;
			traceNode.setStackTraces(Arrays.copyOfRange(stackTraces, 2, to));
		}

		// get call ref
		// StackTraceElement[] stackTraces =
		// Thread.currentThread().getStackTrace();
		// StackTraceElement currentStackTrace = stackTraces[2];
		//
		// if(traceNode.getFullMethodName() != null) {
		// //
		// // System.out.println("======== current method : " +
		// method.getDeclaringClass() + "." + method.getName());
		// // for(StackTraceElement stackTrace : stackTraces) {
		// // System.out.println("[" + stackTrace.getClassName() + "].[" +
		// stackTrace.getMethodName() + "]");
		// // }
		//
		// TraceNode parent = null;
		//
		// // 倒序查找，判断每个node是否为当前节点的父节点
		// // 判断原理： 如果是父节点，必然出现在当前节点的堆栈数组后面
		// List<TraceNode> traceNodes = globalTrace.getTraceNodes();
		// int size = traceNodes.size();
		// if(size > 0) {
		// for(int index = size - 1 ; index >= 0; index --) {
		// TraceNode temp = traceNodes.get(index);
		// boolean find = false;
		// for(int stackIndex = 3; stackIndex < stackTraces.length ;
		// stackIndex++) {
		// StackTraceElement tempStackTrace = stackTraces[stackIndex];
		// if(tempStackTrace.getClassName().equals(temp.getClassName()) &&
		// tempStackTrace.getMethodName().equals(temp.getMethodName())) {
		// // if match break;
		// find = true;
		// break;
		// }
		// }
		// if(find) {
		// parent = temp;
		// break;
		// }
		// }
		// }
		// if(parent != null) {
		// traceNode.setParent(parent);
		// parent.getChildren().add(traceNode);
		// }
		// }
		//
		//

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
				Map<String, Object> vars = new HashMap<String, Object>();
				vars.put("response", response);
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
				// System.out.println(new
				// ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(globalTrace));
				// 异步推送数据
				OpenTraceTemp.addTrace(globalTrace);
				globalTraceThreadLocal.set(null);
			}
		}
	}

	private static void recordTraceNode(TraceNode traceNode,
			GlobalTrace globalTrace, Method method) {
		String traceId = globalTrace.getTraceId();
		long beginTimeMillis = System.currentTimeMillis();
		traceNode.setId(traceId + "." + beginTimeMillis);
		traceNode.setTraceId(traceId);
		traceNode.setClassName(method.getDeclaringClass().getName());
		traceNode.setBeginTimeMillis(beginTimeMillis);
		traceNode.setMethodName(method.getName());
		traceNode.setFullMethodName(method.toGenericString());
		traceNode.setKey(globalTrace.generateTraceNodeKey());

		globalTrace.getTraceNodes().add(traceNode);

		if (globalTrace.getTraceNodes().size() == 1) {
			globalTrace.setEntryStackTraceIndex(Thread.currentThread()
					.getStackTrace().length - 4);
		}

		globalTrace.setErrorFlag(false);
		globalTrace.setStackTrace(null);
	}

}
