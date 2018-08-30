package com.boco.mis.opentrace.plugins.finder;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.boco.mis.opentrace.helper.ClassHelper;
import com.boco.mis.opentrace.plugins.ApmPlugin;

public class PluginExternal extends PluginFinder {

	private String pluginFolder;
	
	public PluginExternal(String pluginFolder,List<ApmPlugin> plugins) {
		 super(plugins);
	     this.pluginFolder = pluginFolder;
	}

	@Override
	void loadPluginClasses() {
		File pluginsDir = new File(pluginFolder);
		if(!pluginsDir.exists() || !pluginsDir.isDirectory()) {
			return ;
		}
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		for(File file : pluginsDir.listFiles()) {
			if(file.isFile() && file.getName().toLowerCase().endsWith(".jar")) {
				classes.addAll(ClassHelper.getClassesFromJar(file,ApmPlugin.class));
			}
		}
		this.pluginClasses = classes;
	}
	
}
