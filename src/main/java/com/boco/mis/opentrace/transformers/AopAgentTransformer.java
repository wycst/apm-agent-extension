//package com.boco.mis.opentrace.transformers;
//
//import java.lang.instrument.ClassFileTransformer;
//import java.lang.instrument.IllegalClassFormatException;
//import java.security.ProtectionDomain;
//
//import javassist.CannotCompileException;
//import javassist.ClassPool;
//import javassist.CodeConverter;
//import javassist.CtClass;
//import javassist.CtMethod;
//import javassist.NotFoundException;
//import javassist.expr.ExprEditor;
//import javassist.expr.MethodCall;
//
//public class AopAgentTransformer implements ClassFileTransformer{
//
//	
//	public static void main(String[] args) {
//		System.out.println("java.util".matches("(java\\.|org\\.|net\\.|sun\\.).*"));
//	}
//	
//    public byte[] transform(ClassLoader loader, String className,
//            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
//            byte[] classfileBuffer) throws IllegalClassFormatException {
//        
//    	byte[] transformed = null;  
//        if(className != null) {
////        	if(className.matches("(jdk/|java/|org/sun/|org/apache/|net/|sun/|com/sun/|com/fasterxml/|javax/).*")) {
////        		return null;
////        	} else {
////        		
////        	}
//        }
//       // System.out.println("Transforming " + className); 
//        ClassPool pool = null;  
//        CtClass cl = null;  
//        try {  
//            pool = ClassPool.getDefault();
//
//            cl = pool.makeClass(new java.io.ByteArrayInputStream(  
//                    classfileBuffer));  
//
////            CtMethod aop_method = pool.get("com.jdktest.instrument.AopMethods").
////                    getDeclaredMethod("aopMethod");
////            System.out.println(aop_method.getLongName());
////            if(cl.getName().matches("(jdk|java|org|net|sun|com.(sun|fasterxml)).*")) {
////        		return null;
////        	} 
////            System.out.println("=====" + cl.getName());
//            
//            
//            CodeConverter convert = new CodeConverter();
//
//            if (cl.isInterface() == false) {  
//                CtMethod[] methods = cl.getDeclaredMethods();  
//                for (int i = 0; i < methods.length; i++) {  
//                    if (methods[i].isEmpty() == false) {  
//                        AOPInsertMethod(methods[i]);  
//                    }  
//                }  
//                transformed = cl.toBytecode();  
//            }  
//        } catch (Exception e) {  
//            System.err.println("Could not instrument  " + className  
//                    + ",  exception : " + e.getMessage());  
//        } finally {  
//            if (cl != null) {  
//                cl.detach();  
//            }  
//        }  
//        return transformed;  
//    }
//
//    private void AOPInsertMethod(CtMethod method) throws NotFoundException,CannotCompileException {
//    	
//    	//situation 1:添加监控时间
//        method.instrument(new ExprEditor() {  
//            public void edit(MethodCall m) throws CannotCompileException {  
//                m.replace("{ long stime = System.currentTimeMillis(); $_ = $proceed($$);System.out.println(\""
//                        + m.getClassName() + "." + m.getMethodName()
//                        + " cost:\" + (System.currentTimeMillis() - stime) + \" ms\");}");
//            }
//        }); 
//        //situation 2:在方法体前后语句
////      method.insertBefore("System.out.println(\"enter method\");");
////      method.insertAfter("System.out.println(\"leave method\");");
//    }
//
//}