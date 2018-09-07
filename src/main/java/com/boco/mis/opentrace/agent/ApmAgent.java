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

import com.boco.mis.opentrace.conf.ApmConfCenter;
import com.boco.mis.opentrace.interceptors.NewTraceInterceptor;
import com.boco.mis.opentrace.interceptors.TraceInterceptor;

public class ApmAgent {

	public static void premain(String agentArgs, Instrumentation inst) {

		System.out.println(" agent collect begin ...");
		System.out.println(" agent home " + ApmConfCenter.AGENT_HOME);
		if(!ApmConfCenter.IS_CONFIG) {
			System.out.println(ApmConfCenter.AGENT_HOME + "/conf/agent-collect-conf.properties  is not exist ");
			return ;
		}
		
		// Interceptor
		AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {

			@Override
			public Builder<?> transform(Builder<?> builder,
					TypeDescription typeDescription, ClassLoader classLoader,
					JavaModule arg3) {
				// System.out.println("typeDescription : " + typeDescription.getName());
				
				if(typeDescription.getName().startsWith("com.boco.mis.opentrace")) {
					System.out.println(typeDescription.getName() + "/" + arg3);
				}
				
				return builder
						.method(ElementMatchers.isDeclaredBy(typeDescription).and(ElementMatchers.not(ElementMatchers.isGetter().or(ElementMatchers.isSetter())))) 
						.intercept(MethodDelegation.to(NewTraceInterceptor.class)); 

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

		Junction<? super TypeDescription> excludesFunc = ApmConfCenter.baseExcludesElementMatcher();
		Junction<? super TypeDescription> includesFunc = ApmConfCenter.baseIncludesElementMatcher();
		ElementMatcher<? super TypeDescription> matchers = ElementMatchers.not(
				ElementMatchers.isInterface().or(excludesFunc)).or(includesFunc);
		
		builder.type(matchers).transform(transformer).with(listener).installOn(inst);
		
	}


}
