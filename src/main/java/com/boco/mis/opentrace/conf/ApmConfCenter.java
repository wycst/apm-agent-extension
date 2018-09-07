package com.boco.mis.opentrace.conf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.boco.mis.opentrace.helper.ClassHelper;
import com.boco.mis.opentrace.plugins.ApmPlugin;
import com.boco.mis.opentrace.plugins.finder.PluginBuiltIn;
import com.boco.mis.opentrace.plugins.finder.PluginExternal;
import com.boco.mis.opentrace.plugins.finder.PluginFinder;

import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher.Junction;
import net.bytebuddy.matcher.ElementMatchers;
/**
 * 配置中心
 * @author wangyunchao
 *
 * 2018年8月30日 上午9:22:14
 */
public class ApmConfCenter {

	public static final String APM_TRACELOGGING_KEY = "apm.tracelogging" ;
	public static final String APM_RESOURCES_EXCLUDE_SUFFIXS_KEY = "apm.resources.exclude.suffixs" ;
	public static final String APM_INTERCEPT_EXCLUDES_PACKAGES_KEY = "apm.intercept.excludes.packages";
	public static final String APM_INTERCEPT_INCLUDES_PACKAGES_KEY = "apm.intercept.includes.packages";
	
	public static final boolean IS_CONFIG ;
	public static final String AGENT_HOME ;
	public static final String COLLECT_HOST ;
	public static final String APM_RESOURCES_EXCLUDE_SUFFIXS ;
	public static final String APM_INTERCEPT_EXCLUDES_PACKAGES;
	public static final String APM_INTERCEPT_INCLUDES_PACKAGES;
	
	public static final String APM_TRACELOGGING;
	
    private static Properties PROPERTIES = new Properties();
	// 插件
    private static final List<ApmPlugin> PLUGINS = new ArrayList<ApmPlugin>();
    
	static {
		// 加载配置文件
		String path = ApmConfCenter.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			path = java.net.URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		// /D:/gitws/apm-agent-extension/target/apm-agent-extension-0.0.2-SNAPSHOT.jar
		if (path.endsWith(".jar")) {
			path = path.substring(0, path.lastIndexOf("/") + 1);
		}
		File file = new File(path);
		String filePath = file.getAbsolutePath();
		AGENT_HOME = filePath;
		// loading conf
		// dir : {AGENT_HOME}/conf/
		IS_CONFIG = initializeProperties();
		COLLECT_HOST = PROPERTIES.getProperty("apm.collect.server");
	
		APM_TRACELOGGING = PROPERTIES.getProperty(APM_TRACELOGGING_KEY) == null ? "off" : PROPERTIES.getProperty(APM_TRACELOGGING_KEY);
		APM_RESOURCES_EXCLUDE_SUFFIXS = PROPERTIES.getProperty(APM_RESOURCES_EXCLUDE_SUFFIXS_KEY) == null ? "txt,js,css,gif,jpg,png,ico" : PROPERTIES.getProperty(APM_RESOURCES_EXCLUDE_SUFFIXS_KEY);
		
		APM_INTERCEPT_EXCLUDES_PACKAGES = PROPERTIES.getProperty(APM_INTERCEPT_EXCLUDES_PACKAGES_KEY);
		APM_INTERCEPT_INCLUDES_PACKAGES = PROPERTIES.getProperty(APM_INTERCEPT_INCLUDES_PACKAGES_KEY);
		
		loadDependencyLibrary();
	    // loading plugins
		// dir : {AGENT_HOME}/plugins/
		// 设计：
		// 1 先加载 {AGENT_HOME}/lib/下面是所有的依赖包
		// 2 动态加载plugins下面所有的jar
		// 3 通过PluginFinder获取接口ApmPlugin的所有实现类添加到plugins中
		// 内置的插件
		loadPlugins();
		
	}
	
	private static boolean initializeProperties() {
		
		String fileSeparator = File.separator;
		String confPath = AGENT_HOME + fileSeparator + "conf" + fileSeparator + "agent-collect-conf.properties";
		FileReader fr = null;
		try {
			File confFile = new File(confPath);
			if(!confFile.exists()) {
				return false;
			}
			PROPERTIES.load(fr = new FileReader(new File(confPath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	private static void loadDependencyLibrary() {
		ClassHelper.loadJars(AGENT_HOME + File.separator + "lib");
	}

	private static void loadPlugins() {
		// 内置插件
		PluginFinder pluginBuiltIn = new PluginBuiltIn(PLUGINS);
		pluginBuiltIn.loadPlugins();
	
		// 拓展插件
		PluginFinder pluginExternal = new PluginExternal(AGENT_HOME + File.separator + "plugins",PLUGINS);
		pluginExternal.loadPlugins();
	}

	public static String getCollectHost() {
		return COLLECT_HOST;
	}
	
	public static String getAgentHome() {
		return AGENT_HOME;
	}
	
	public static String getConfValue(String confKey) {
		return PROPERTIES.getProperty(confKey);
	}
	
	public static Junction<? super TypeDescription> baseIncludesElementMatcher() {
		Junction<? super NamedElement> func = ElementMatchers.none();
	   
		// load 内置插件
		if(PLUGINS.size() > 0) {
	    	for (ApmPlugin plugin : PLUGINS) {
	    		String entryClass = plugin.getEntryClass();
	    		func = func.or(ElementMatchers.named(entryClass));
			}
	    }
	    
	    // load conf
	    if(APM_INTERCEPT_INCLUDES_PACKAGES != null && !APM_INTERCEPT_INCLUDES_PACKAGES.equals("-1")) {
			String[] packages = APM_INTERCEPT_INCLUDES_PACKAGES.split(",");
			for(String _package : packages) {
				if(_package.matches("[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*")) {
					func = func.or(ElementMatchers.nameStartsWith(_package + "."));
				}
			}
		}
	    
	    return func;
	    
//		return ElementMatchers
//						.named("org.apache.catalina.connector.CoyoteAdapter")
//				.or(ElementMatchers
//						.named("org.apache.catalina.core.ApplicationFilterChain"))
//				// j2ee HttpServlet
//				.or(ElementMatchers
//						.named("javax.servlet.http.HttpServlet"))
//				// jsp servlet 暂时屏蔽防止跳转或重定向servlet二次请求
////				.or(ElementMatchers
////						.named("org.apache.jasper.servlet.JspServlet"))
//				// spring mvc
//				.or(ElementMatchers
//						.named("org.springframework.web.servlet.DispatcherServlet"))
//				// struts2
//				.or(ElementMatchers
//						.named("org.apache.struts2.dispatcher.Dispatcher"))
//				// mysql io
//				.or(ElementMatchers
//						.named("com.mysql.jdbc.MysqlIO"))
//				// redis
//				.or(ElementMatchers
//						.named("redis.clients.jedis.Jedis"))
//						;
	}
	
	public static Junction<? super TypeDescription> baseExcludesElementMatcher() {
		
		Junction<? super NamedElement> func = ElementMatchers.none();
		
		if(APM_INTERCEPT_EXCLUDES_PACKAGES != null && !APM_INTERCEPT_EXCLUDES_PACKAGES.equals("-1")) {
			String[] packages = APM_INTERCEPT_EXCLUDES_PACKAGES.split(",");
			for(String _package : packages) {
				if(_package.matches("[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*")) {
					func = func.or(ElementMatchers.nameStartsWith(_package + "."));
				}
			}
		}
		
		return 
				// 接口或抽象类
				func.or(ElementMatchers.isInterface())/*.or(ElementMatchers.isAbstract())*/
				// 当前工具包
				.or(ElementMatchers.nameStartsWith("com.boco.mis.opentrace"))
				// jdk packages
				.or(ElementMatchers.nameStartsWith("java."))
				.or(ElementMatchers.nameStartsWith("javax."))
				// 工具包
				.or(ElementMatchers.nameStartsWith("com.sun."))
				.or(ElementMatchers.nameStartsWith("com.ctc.wstx."))
				.or(ElementMatchers.nameStartsWith("com.google.common."))
				.or(ElementMatchers.nameStartsWith("com.google.protobuf."))
				.or(ElementMatchers.nameStartsWith("com.carrotsearch."))
				.or(ElementMatchers.nameStartsWith("com.fasterxml."))
				.or(ElementMatchers.nameStartsWith("com.alibaba."))
				.or(ElementMatchers.nameStartsWith("com.mchange."))
				.or(ElementMatchers.nameStartsWith("com.ibatis."))
				.or(ElementMatchers.nameStartsWith("com.opensymphony."))
				.or(ElementMatchers.nameStartsWith("com.sshtools."))
				.or(ElementMatchers.nameStartsWith("com.atomikos."))
				.or(ElementMatchers.nameStartsWith("com.thoughtworks."))
				.or(ElementMatchers.nameStartsWith("javassist."))
				.or(ElementMatchers.nameStartsWith("sun."))
				.or(ElementMatchers.nameStartsWith("org."))
				.or(ElementMatchers.nameStartsWith("antlr."))
				.or(ElementMatchers.nameStartsWith("net."))
				.or(ElementMatchers.nameStartsWith("freemarker."))
				.or(ElementMatchers.nameStartsWith("ognl."))
				// 数据库
				.or(ElementMatchers.nameStartsWith("oracle."))
				.or(ElementMatchers.nameStartsWith("redis.clients."))
				.or(ElementMatchers.nameStartsWith("com.mysql."))
				// 代理类
				.or(ElementMatchers.nameStartsWith("$"))
				;
	}

	public static List<ApmPlugin> getPlugins() {
		return PLUGINS;
	}
	
	
}
