package com.boco.mis.opentrace.plugins.finder;

import java.util.List;

import com.boco.mis.opentrace.helper.ClassHelper;
import com.boco.mis.opentrace.plugins.ApmPlugin;

public class PluginBuiltIn extends PluginFinder {

	private static Class<?> pluginInterClass = ApmPlugin.class;
	private static String pluginPackage = pluginInterClass.getPackage().getName();
	
	public PluginBuiltIn(List<ApmPlugin> plugins) {
		super(plugins);
	}
	
	@Override
	void loadPluginClasses() {
		pluginClasses = ClassHelper.getClasses(pluginPackage,pluginInterClass);
	}
}
