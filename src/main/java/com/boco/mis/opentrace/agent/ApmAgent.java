package com.boco.mis.opentrace.agent;

import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatcher.Junction;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import com.boco.mis.opentrace.config.ApmConfCenter;
import com.boco.mis.opentrace.interceptors.TraceInterceptor;

public class ApmAgent {

	public static void premain(String agentArgs, Instrumentation inst) {

		System.out.println(" agent collect begin ...");
		System.out.println(" agent home " + ApmConfCenter.AGENT_HOME);
		if(!ApmConfCenter.IS_CONFIG) {
			System.out.println(ApmConfCenter.AGENT_HOME + "/conf/agent-collect-conf.properties  is not exist ");
			return ;
		}
		
		// bytebuddy 拦截方法
		AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {

			@Override
			public Builder<?> transform(Builder<?> builder,
					TypeDescription typeDescription, ClassLoader classLoader,
					JavaModule arg3) {
				// System.out.println("typeDescription : " + typeDescription.getName());
				return builder
						.method(ElementMatchers.isDeclaredBy(typeDescription).and(ElementMatchers.not(ElementMatchers.isGetter().or(ElementMatchers.isSetter())))) 
						.intercept(MethodDelegation.to(TraceInterceptor.class)); 

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
		ElementMatcher<? super TypeDescription> matchers = ElementMatchers.not(
				ElementMatchers.isInterface().or(excludesFunc)).or(includesFunc);
		
		builder.type(matchers).transform(transformer).with(listener).installOn(inst);
		
	}

	private static Junction<? super TypeDescription> baseIncludesElementMatcher() {
		return ElementMatchers
						.named("org.apache.catalina.connector.CoyoteAdapter")
				.or(ElementMatchers
						.named("org.apache.catalina.core.ApplicationFilterChain"))
				// j2ee HttpServlet
				.or(ElementMatchers
						.named("javax.servlet.http.HttpServlet"))
				// jsp servlet 暂时屏蔽防止跳转或重定向servlet二次请求
//				.or(ElementMatchers
//						.named("org.apache.jasper.servlet.JspServlet"))
				// spring mvc
				.or(ElementMatchers
						.named("org.springframework.web.servlet.DispatcherServlet"))
				// struts2
				.or(ElementMatchers
						.named("org.apache.struts2.dispatcher.Dispatcher"))
				// mysql io
				.or(ElementMatchers
						.named("com.mysql.jdbc.MysqlIO"))
				// redis
				.or(ElementMatchers
						.named("redis.clients.jedis.Jedis"))
						;
	}

	private static Junction<? super TypeDescription> baseExcludesElementMatcher() {
		
		return 
				// 接口或抽象类
				ElementMatchers.isInterface()/*.or(ElementMatchers.isAbstract())*/
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
	
}
