package com.boco.mis.opentrace.agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.util.Properties;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Identified.Narrowable;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatcher.Junction;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import com.boco.mis.opentrace.interceptors.TraceInterceptor;

public class ApmAgent {

	private static final String AGENT_HOME ;
	
	static {
		// 加载配置文件
		String path = ApmAgent.class.getProtectionDomain().getCodeSource().getLocation().getPath();
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
	}
	
	
	public static void premain(String agentArgs,Instrumentation inst) {
		
		initialize();
		// 添加trace转换器
//		inst.addTransformer(new AopAgentTransformer());
//		if(true) return ;
	
		// bytebuddy 拦截方法
		AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
			
			@Override
			public Builder<?> transform(Builder<?> builder,
					TypeDescription typeDescription, ClassLoader classLoader,
					JavaModule arg3) {
				
//				System.out.println("package : " +  typeDescription.getName());
				if(typeDescription.getName().contains("com.boco.mis.opentrace")) {
					// 过滤掉当前包下面的类
					return builder;
				} 
				
				return builder
//						.method(ElementMatchers.<MethodDescription> any())
						.method(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class).or(ElementMatchers.isGetter()).or(ElementMatchers.isSetter()))) // 拦截任意方法
//						.method(ElementMatchers.<MethodDescription> any().and(ElementMatchers.not(ElementMatchers.namedIgnoreCase("toString")))) // 拦截任意方法
						.intercept(MethodDelegation.to(TraceInterceptor.class)); // 委托
			            
			}           
		};

		AgentBuilder.Listener listener = new AgentBuilder.Listener() {

			@Override
			public void onComplete(String typeName, ClassLoader classLoader,
					JavaModule module, boolean arg3) {
			}

			@Override
			public void onDiscovery(String arg0, ClassLoader arg1,
					JavaModule arg2, boolean arg3) {
			}

			@Override
			public void onError(String typeName, ClassLoader classLoader,
					JavaModule module, boolean arg3, Throwable throwable) {
			}

			@Override
			public void onIgnored(TypeDescription arg0, ClassLoader arg1,
					JavaModule arg2, boolean arg3) {
			
			}

			@Override
			public void onTransformation(TypeDescription arg0,
					ClassLoader arg1, JavaModule arg2, boolean arg3,
					DynamicType arg4) {
				
			}
		};
		
		
		AgentBuilder builder = new AgentBuilder.Default();
		
		Junction<? super TypeDescription> excludesFunc = baseExcludesElementMatcher();
		Junction<? super TypeDescription> includesFunc = baseIncludesElementMatcher();
		ElementMatcher<? super TypeDescription> matchers = ElementMatchers.not(excludesFunc).or(includesFunc);
		Narrowable narrowable = builder.type(matchers);
		narrowable.transform(transformer).with(listener).installOn(inst);
		
		
//		new AgentBuilder.Default()
////				.type(ElementMatchers.nameStartsWith("com.boco.mis")) // 指定需要拦截的类
//				//.type(ElementMatchers.nameMatches(".*(org.apache.struts2|redis.clients|org.apache.catalina.connector.CoyoteAdapter|org.apache.catalina.core.ApplicationFilterChain|org.apache.http.impl.client|com.mysql.jdbc|com.boco.(workflow|mss|mis)|org.springframework.web.servlet.DispatcherServlet).*")) // 指定需要拦截的类
//				.type(ElementMatchers.any()) // 指定需要拦截的类
//				.transform(transformer).with(listener).installOn(inst);
	}



	private static Junction<? super TypeDescription> baseIncludesElementMatcher() {
		return ElementMatchers.named("org.apache.catalina.connector.CoyoteAdapter")
			   .or(ElementMatchers.named("org.apache.catalina.core.ApplicationFilterChain"))
			   // j2ee HttpServlet
			   .or(ElementMatchers.named("javax.servlet.http.HttpServlet"))
			   // jsp servlet
			   .or(ElementMatchers.named("org.apache.jasper.servlet.JspServlet"))
			   // spring mvc
			   .or(ElementMatchers.named("org.springframework.web.servlet.DispatcherServlet"))
			   // struts2 
			   .or(ElementMatchers.named("org.apache.struts2.dispatcher.Dispatcher"))
			   // mysql 连接
			   .or(ElementMatchers.named("com.mysql.jdbc.ConnectionImpl"))
			   // redis
			   .or(ElementMatchers.named("redis.clients.jedis.Jedis"))
				;
	}

	private static Junction<? super TypeDescription> baseExcludesElementMatcher() {
		
		return ElementMatchers.nameStartsWith("org.apache.")
			   // 当前工具包	
			   .or(ElementMatchers.nameStartsWith("com.boco.mis.opentrace"))
			   // jdk packages
			   .or(ElementMatchers.nameMatches("com.(sun|io|util)..*"))
			   .or(ElementMatchers.nameStartsWith("java."))
			   .or(ElementMatchers.nameStartsWith("javax."))
			   // 工具包
			   .or(ElementMatchers.nameStartsWith("com.ctc.wstx."))
			   .or(ElementMatchers.nameStartsWith("com.google.protobuf."))
			   .or(ElementMatchers.nameStartsWith("com.fasterxml."))
			   .or(ElementMatchers.nameStartsWith("com.alibaba."))
			   .or(ElementMatchers.nameStartsWith("com.mchange."))
			   .or(ElementMatchers.nameStartsWith("com.ibatis."))
			   .or(ElementMatchers.nameStartsWith("com.opensymphony."))
			   .or(ElementMatchers.nameStartsWith("com.sshtools."))
			   .or(ElementMatchers.nameStartsWith("com.atomikos."))
			   .or(ElementMatchers.nameStartsWith("com.mysql."))
			   .or(ElementMatchers.nameStartsWith("com.thoughtworks."))
			   .or(ElementMatchers.nameStartsWith("oracle."))
			   .or(ElementMatchers.nameMatches("javassist\\..*"))
			   .or(ElementMatchers.nameMatches("sun\\..*"))
			   .or(ElementMatchers.nameMatches("org\\..*"))
			   .or(ElementMatchers.nameMatches("antlr\\..*"))
			   .or(ElementMatchers.nameMatches("net\\..*"))
			   .or(ElementMatchers.nameMatches("freemarker\\..*"))
			   .or(ElementMatchers.nameMatches("ognl\\..*"))
			   // 代理类
			   .or(ElementMatchers.nameStartsWith("$"))
			   ;
	}



	private static void initialize() {
		
		String fileSeparator = File.separator;
		String confPath = AGENT_HOME + fileSeparator + "conf" + fileSeparator + "agent-collect-conf.properties";
		Properties properties = new Properties();
		FileReader fr = null;
		try {
			File confFile = new File(confPath);
			if(!confFile.exists()) {
				return ;
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
	}
}
