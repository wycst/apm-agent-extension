package com.boco.mis.opentrace.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class ApmConfCenter {

	public static boolean IS_CONFIG ;
	public static final String AGENT_HOME ;
	public static final String COLLECT_HOST ;
	
    private static Properties properties;
	
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
		properties = new Properties();
		IS_CONFIG = initializeProperties();
		COLLECT_HOST = properties.getProperty("apm.collect.server");
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
			properties.load(fr = new FileReader(new File(confPath)));
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
	
	public static String getCollectHost() {
		return COLLECT_HOST;
	}
	
	public static String getAgentHome() {
		return AGENT_HOME;
	}
	
	public static String getConfValue(String confKey) {
		return properties.getProperty(confKey);
	}
	
}
