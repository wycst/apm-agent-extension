package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.data.server.Redis;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.reflect.AsmInvoke;

public class RedisPlugin extends ApmPlugin {

	private final String pluginName = "redis";

	private final String entryClass = "redis.clients.jedis.Jedis";

	private final String interceptMethod = null;

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

		if (args.length > 0) {
			InterceptorHelper.recordTraceNode(traceNode, globalTrace, method);
			traceNode.setModule("redis");
			try {
				Field argument0 = callable.getClass().getDeclaredField(
						"argument0");
				argument0.setAccessible(true);
				Object jedis = argument0.get(callable);

				Object jedisClient = AsmInvoke.invoke(jedis, jedis.getClass(),
						"getClient");

				String db = AsmInvoke.invoke(jedisClient,
						jedisClient.getClass(), "getDB")
						+ "";

				String host = AsmInvoke.invoke(jedisClient,
						jedisClient.getClass(), "getHost")
						+ "";
				String port = AsmInvoke.invoke(jedisClient,
						jedisClient.getClass(), "getPort")
						+ "";

				traceNode.setTraceType("redis");

				Redis redis = new Redis();
				redis.setDatabase(db);
				redis.setHost(host);
				redis.setPort(port);
				redis.setCategory("database");
				redis.setType("redis");

				traceNode.setTraceCommand("host:" + redis.getHostPortPair()
						+ "\n  db:" + db + "\n  " + method.getName() + ":"
						+ Arrays.asList(args));

				globalTrace.addServer(redis);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
