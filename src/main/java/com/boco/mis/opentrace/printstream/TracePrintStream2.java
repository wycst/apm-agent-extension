//package com.boco.mis.opentrace.printstream;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.io.UnsupportedEncodingException;
//
//public class TracePrintStream2 extends PrintStream {
//
//	public static TracePrintStream2 getOut() {
//		return new TracePrintStream2(System.out);
//	}
//
//	public static TracePrintStream2 getErr() {
//		return new TracePrintStream2(System.err);
//	}
//
//	private TracePrintStream2(File file, String csn)
//			throws FileNotFoundException, UnsupportedEncodingException {
//		super(file, csn);
//		// TODO Auto-generated constructor stub
//	}
//
//	private TracePrintStream2(File file) throws FileNotFoundException {
//		super(file);
//		// TODO Auto-generated constructor stub
//	}
//
//	private TracePrintStream2(OutputStream out, boolean autoFlush, String encoding)
//			throws UnsupportedEncodingException {
//		super(out, autoFlush, encoding);
//		// TODO Auto-generated constructor stub
//	}
//
//	private TracePrintStream2(OutputStream out, boolean autoFlush) {
//		super(out, autoFlush);
//		// TODO Auto-generated constructor stub
//	}
//
//	private TracePrintStream2(OutputStream out) {
//		super(out);
//		// TODO Auto-generated constructor stub
//	}
//
//	private TracePrintStream2(String fileName, String csn)
//			throws FileNotFoundException, UnsupportedEncodingException {
//		super(fileName, csn);
//		// TODO Auto-generated constructor stub
//	}
//
//	private TracePrintStream2(String fileName) throws FileNotFoundException {
//		super(fileName);
//		// TODO Auto-generated constructor stub
//	}
//	
//	@Override
//	public void print(boolean b) {
//		out(b ? "true" : "false", false);
//	}
//
//	@Override
//	public void print(char c) {
//		out(toString(c), false);
//	}
//
//	private String toString(Object c) {
//		return String.valueOf(c);
//	}
//
//	@Override
//	public void print(int i) {
//		out(toString(i), false);
//	}
//
//	@Override
//	public void print(long l) {
//		out(toString(l), false);
//	}
//
//	@Override
//	public void print(float f) {
//		out(toString(f), false);
//	}
//
//	@Override
//	public void print(double d) {
//		out(toString(d), false);
//	}
//
//	@Override
//	public void print(char[] s) {
//		out(toString(s), false);
//	}
//
//	@Override
//	public void print(Object obj) {
//		out(toString(obj), false);
//	}
//
//	@Override
//	public void print(String x) {
//		// TODO Auto-generated method stub
//		out(x,false);
//	}
//	
//	@Override
//	public void println(boolean x) {
//		println(x ? "true" : "false");
//	}
//
//	@Override
//	public void println(char x) {
//		println(toString(x));
//	}
//
//	@Override
//	public void println(int x) {
//		println(toString(x));
//	}
//
//	@Override
//	public void println(long x) {
//		println(toString(x));
//	}
//
//	@Override
//	public void println(float x) {
//		println(toString(x));
//	}
//
//	@Override
//	public void println(double x) {
//		println(toString(x));
//	}
//
//	@Override
//	public void println(char[] x) {
//		println(toString(x));
//	}
//
//	@Override
//	public void println(Object x) {
//		println(toString(x));
//	}
//
//	public void println(String x) {
//		out(x,true);
//	}
//	
//	public void println() {
//		// TODO Auto-generated method stub
//		out("",true);
//	}
//
//	private void out(String x,boolean newLine) {
//		super.print(x);
//		if(newLine) {
//			super.println();
//		}
//	}
//	
//	public static void main(String[] args) {
//		System.setOut(new TracePrintStream2(System.out));
//		System.setErr(new TracePrintStream2(System.err));
//		
//		System.out.println("123");
//	}
//	
//}
