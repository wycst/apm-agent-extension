package com.boco.mis.opentrace.printstream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public abstract class AbstractPrintStream extends PrintStream {

	private String printType;
	
	public AbstractPrintStream(OutputStream out, String printType) {
		super(out);
		this.printType = printType;
	}
	
	public AbstractPrintStream(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
		// TODO Auto-generated constructor stub
	}

	public AbstractPrintStream(File file) throws FileNotFoundException {
		super(file);
		// TODO Auto-generated constructor stub
	}

	public AbstractPrintStream(OutputStream out, boolean autoFlush, String encoding)
			throws UnsupportedEncodingException {
		super(out, autoFlush, encoding);
		// TODO Auto-generated constructor stub
	}

	public AbstractPrintStream(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
		// TODO Auto-generated constructor stub
	}

	public AbstractPrintStream(OutputStream out) {
		super(out);
		// TODO Auto-generated constructor stub
	}

	public AbstractPrintStream(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
		// TODO Auto-generated constructor stub
	}

	public AbstractPrintStream(String fileName) throws FileNotFoundException {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void print(boolean b) {
		out(b ? "true" : "false", false);
	}

	@Override
	public void print(char c) {
		out(toString(c), false);
	}

	private String toString(Object c) {
		return String.valueOf(c);
	}

	@Override
	public void print(int i) {
		out(toString(i), false);
	}

	@Override
	public void print(long l) {
		out(toString(l), false);
	}

	@Override
	public void print(float f) {
		out(toString(f), false);
	}

	@Override
	public void print(double d) {
		out(toString(d), false);
	}

	@Override
	public void print(char[] s) {
		out(toString(s), false);
	}

	@Override
	public void print(Object obj) {
		out(toString(obj), false);
	}

	@Override
	public void print(String x) {
		// TODO Auto-generated method stub
		out(x,false);
	}
	
	@Override
	public void println(boolean x) {
		println(x ? "true" : "false");
	}

	@Override
	public void println(char x) {
		println(toString(x));
	}

	@Override
	public void println(int x) {
		println(toString(x));
	}

	@Override
	public void println(long x) {
		println(toString(x));
	}

	@Override
	public void println(float x) {
		println(toString(x));
	}

	@Override
	public void println(double x) {
		println(toString(x));
	}

	@Override
	public void println(char[] x) {
		println(toString(x));
	}

	@Override
	public void println(Object x) {
		println(toString(x));
	}

	public void println(String x) {
		out(x,true);
	}
	
	public void println() {
		// TODO Auto-generated method stub
		out("",true);
	}

	public void out(String x) {
		super.print(x);
	}
	
	public void newLine() {
		super.println();
	}
	
	private void out(String x, boolean b) {
		out(x,b,printType);
	}
	
	abstract void out(String x, boolean b,String printType) ;
	
}
