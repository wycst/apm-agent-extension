package com.boco.mis.opentrace.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StackTraceUtils {

	public static boolean printStackTrace = false;
	
	public static StackTraceElement getCurrentStackTrace() {
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		if (stacks == null || stacks.length < 2) {
			return null;
		}
		for(StackTraceElement stack : stacks) {
			if(stack.getClassName().contains("java.lang.Thread") || stack.getClassName().contains("com.boco.mis.opentrace")) {
				continue;
			}
			printStackTrace(stack);
			return stack;
		}
		return null;
	}
	
	public static StackTraceElement getParentStackTrace() {
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		if (stacks == null || stacks.length < 3) {
			return null;
		}
		int i = 0;
		for(StackTraceElement stack : stacks) {
			if(stack.getClassName().contains("java.lang.Thread") || stack.getClassName().contains("com.boco.mis.opentrace")) {
				continue;
			}
			if(i++ == 0) continue;
			printStackTrace(stack);
			return stack;
		}
		return null;
	}

	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}
	
	private static void printStackTrace(StackTraceElement stack) {
		if(printStackTrace) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SS");
			System.out.println(sdf.format(new Date()) + " [INFO] at "
					+ stack.getClassName() + "." + stack.getMethodName()
					+ "                   (" + stack.getFileName() + ":"
					+ stack.getLineNumber() + ")");
		}
	}
}
