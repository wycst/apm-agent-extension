package com.boco.mis.opentrace.plugins.finder;

import java.util.List;
import java.util.Set;

import com.boco.mis.opentrace.plugins.ApmPlugin;

public abstract class PluginFinder {

	abstract void loadPluginClasses();
	
	PluginFinder(List<ApmPlugin> plugins) {
		this.plugins = plugins;
	}
	
	Set<Class<?>> pluginClasses ;
	
	List<ApmPlugin> plugins;
	
	public void loadPlugins() {
		loadPluginClasses();
		if(pluginClasses == null) 
			return ;
		
		for(Class<?> pluginClass : pluginClasses) {
			try {
				ApmPlugin apmPlugin = (ApmPlugin) pluginClass.newInstance();
				plugins.add(apmPlugin);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
}
