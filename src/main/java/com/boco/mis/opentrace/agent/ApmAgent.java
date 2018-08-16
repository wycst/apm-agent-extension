package com.boco.mis.opentrace.agent;

import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import com.boco.mis.opentrace.interceptors.TraceInterceptor;
import com.boco.mis.opentrace.transformers.AopAgentTransformer;
import com.boco.mis.opentrace.transformers.TraceClassFileTransformer;

public class ApmAgent {

	
	public static void premain(String agentArgs,Instrumentation inst) {
		
		// 添加trace转换器
//		inst.addTransformer(new AopAgentTransformer());
//		if(true) return ;
		
		// bytebuddy 拦截方法
		AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
			
			@Override
			public Builder<?> transform(Builder<?> builder,
					TypeDescription typeDescription, ClassLoader classLoader,
					JavaModule arg3) {
//				System.out.println(typeDescription);
				if(typeDescription.getName().contains("com.boco.mis.opentrace")) {
					// 过滤掉当前包下面的类
					return builder;
				} 
				
//				if(!typeDescription.getName().contains("com.boco.mis") && !typeDescription.getName().contains("org.springframework")) {
//					return builder;
//				} 
				
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
				// TODO Auto-generated method stub

			}

			@Override
			public void onDiscovery(String arg0, ClassLoader arg1,
					JavaModule arg2, boolean arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(String typeName, ClassLoader classLoader,
					JavaModule module, boolean arg3, Throwable throwable) {

			}

			@Override
			public void onIgnored(TypeDescription arg0, ClassLoader arg1,
					JavaModule arg2, boolean arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTransformation(TypeDescription arg0,
					ClassLoader arg1, JavaModule arg2, boolean arg3,
					DynamicType arg4) {
				// TODO Auto-generated method stub
			}
		};
		

		new AgentBuilder.Default()
//				.type(ElementMatchers.nameStartsWith("com.boco.mis")) // 指定需要拦截的类
				.type(ElementMatchers.nameMatches(".*(org.apache.struts2|redis.clients|org.apache.catalina.connector.CoyoteAdapter|org.apache.catalina.core.ApplicationFilterChain|org.apache.http.impl.client|com.mysql.jdbc|com.boco.(workflow|mss|mis)|org.springframework.web.servlet.DispatcherServlet).*")) // 指定需要拦截的类
//				.type(ElementMatchers.any()) // 指定需要拦截的类
				.transform(transformer).with(listener).installOn(inst);
	}
}
