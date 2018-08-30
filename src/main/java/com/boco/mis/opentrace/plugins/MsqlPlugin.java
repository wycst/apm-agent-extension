package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.data.server.Database;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.reflect.AsmInvoke;

public class MsqlPlugin extends ApmPlugin {

	private final String pluginName = "SpringMVC";

	private final String entryClass = "com.mysql.jdbc.MysqlIO";

	private final String interceptMethod = "sqlQueryDirect";

	private final String targetType = "traceNode";

	public String getPluginName() {
		return pluginName;
	}

	public String getEntryClass() {
		return entryClass;
	}

	public String getInterceptMethod() {
		return interceptMethod;
	}

	public String getTargetType() {
		return targetType;
	}

	public void doTrace(Method method, Callable<?> callable, Object[] args,
			GlobalTrace globalTrace, TraceNode traceNode) {

		if (args.length == 10) {
			Object statementImpl = args[0];
			String sql = (String) args[1];
			if (statementImpl != null) {
				InterceptorHelper.recordTraceNode(traceNode, globalTrace,
						method);
				traceNode.setModule("mysql");
				traceNode.setTraceType("sql");
				try {
					Object target = null;
					// callstatement
					if (statementImpl != null) {
						if (args[3] != null) {
							// queryPacket
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

	}
}
