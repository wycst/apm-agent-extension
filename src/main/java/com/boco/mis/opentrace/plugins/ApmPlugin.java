package com.boco.mis.opentrace.plugins;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import com.boco.mis.opentrace.conf.ApmConfCenter;
import com.boco.mis.opentrace.data.trace.GlobalTrace;
import com.boco.mis.opentrace.data.trace.TraceNode;

public abstract class ApmPlugin {

    static String ignoreRequestPattern = "";

	abstract String getPluginName();
	
	public abstract String getEntryClass();
	
	abstract String getInterceptMethod();
	
	abstract String getTargetType();
	
	private TraceResult result = new TraceResult();
	
	static {
		ignoreRequestPattern = ".*\\." + ApmConfCenter.APM_RESOURCES_EXCLUDE_SUFFIXS.replace(',', '|');
	}
	
	public TraceResult getResult() {
		return result;
	}

	abstract void doTrace(Method method, Callable<?> callable,
			 Object[] args, GlobalTrace globalTrace,
			TraceNode traceNode) throws Exception;
	
	boolean entry() {
		return true;
	}
	
	public Object getEntryThis(Callable<?> callable) {
		try {
			Field argument0 = callable.getClass().getDeclaredField(
					"argument0");
			argument0.setAccessible(true);
			Object entryThis = argument0.get(callable);
			if(argument0.getType().getName().equals(getEntryClass())) {
				return entryThis;
			}
		} catch(Exception ex) {
			
		}
		return null;
	}
	
	/**
	 * 
	 * 返回false退出trace
	 * 
	 * @param method
	 * @return
	 */
	boolean entryMethod(Method method) {
		return getInterceptMethod() == null || method.getName().equals(getInterceptMethod());
	}
	
	
	public TraceResult trace(Method method, Callable<?> callable,
			 Object[] args, GlobalTrace globalTrace,
			TraceNode traceNode) {
		result.resetFields();
		if(entry() && method.getDeclaringClass().getName().equals(getEntryClass())) {
			result.setPlugin(this);
			result.setTracePlugin(this.getPluginName());
			if(entryMethod(method)) {
				try {
					doTrace(method, callable, args, globalTrace, traceNode);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				result.setTraced(true);
				return result;
			}
		}
		return result;
	}
	
	public abstract void catchError(Exception ex, TraceNode traceNode);
	
	public abstract void afterCall(boolean globalEntry,TraceNode traceNode, Object[] args);
	
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
