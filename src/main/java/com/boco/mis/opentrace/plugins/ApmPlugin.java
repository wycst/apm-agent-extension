package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;

public abstract class ApmPlugin {

    static String ignoreRequestPattern = ".*\\.(txt|js|css|gif|jpg|png|ico)";

	abstract String getPluginName();
	
	public abstract String getEntryClass();
	
	abstract String getInterceptMethod();
	
	abstract String getTargetType();
	
	private TraceResult result = new TraceResult();
	
	public TraceResult getResult() {
		return result;
	}

	abstract void doTrace(Method method, Callable<?> callable,
			 Object[] args, GlobalTrace globalTrace,
			TraceNode traceNode);
	
	public TraceResult trace(Method method, Callable<?> callable,
			 Object[] args, GlobalTrace globalTrace,
			TraceNode traceNode) {
		result.resetFields();
		if(method.getDeclaringClass().getName().equals(getEntryClass())) {
			result.setPlugin(this);
			result.setTracePlugin(this.getPluginName());
			if(getInterceptMethod() == null || method.getName().equals(getInterceptMethod())) {
				doTrace(method, callable, args, globalTrace, traceNode);
				result.setTraced(true);
				return result;
			}
		}
		return result;
	}
	
	public class TraceResult {
		
		boolean traced;
		
		String tracePlugin;

		boolean globalEntry;
		
		private TraceNode traceNode;
		
		private ApmPlugin plugin;
		
		public TraceResult() {
			
		}
		
		public void resetFields() {
			traced = false;
			tracePlugin = null;
			globalEntry = false;
			traceNode = null;
			plugin = null;
		}
		
		public boolean isTraced() {
			return traced;
		}

		public void setTraced(boolean traced) {
			this.traced = traced;
		}

		public String getTracePlugin() {
			return tracePlugin;
		}

		public void setTracePlugin(String tracePlugin) {
			this.tracePlugin = tracePlugin;
		}

		public boolean isGlobalEntry() {
			return globalEntry;
		}

		public void setGlobalEntry(boolean globalEntry) {
			this.globalEntry = globalEntry;
		}

		public TraceNode getTraceNode() {
			return traceNode;
		}

		public void setTraceNode(TraceNode traceNode) {
			this.traceNode = traceNode;
		}

		public ApmPlugin getPlugin() {
			return plugin;
		}

		public void setPlugin(ApmPlugin plugin) {
			this.plugin = plugin;
		}

	} 
	
}
