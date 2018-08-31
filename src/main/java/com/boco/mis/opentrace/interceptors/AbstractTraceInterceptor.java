package com.boco.mis.opentrace.interceptors;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import com.boco.mis.opentrace.data.trace.TraceNode;
import com.boco.mis.opentrace.helper.InterceptorHelper;
import com.boco.mis.opentrace.plugins.ApmPlugin;
import com.boco.mis.opentrace.plugins.ApmPlugin.TraceResult;

public abstract class AbstractTraceInterceptor {


		
}
