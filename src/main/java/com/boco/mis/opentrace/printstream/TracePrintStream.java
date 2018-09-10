package com.boco.mis.opentrace.printstream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class TracePrintStream extends AbstractPrintStream {

	public static TracePrintStream getOut() {
		return new TracePrintStream(System.out,"System.out");
	}

	public static TracePrintStream getErr() {
		return new TracePrintStream(System.err,"System.err");
	}

	private TracePrintStream(OutputStream out,String type) {
		super(out,type);
	}
	
	private TracePrintStream(File file, String csn)
			throws FileNotFoundException, UnsupportedEncodingException {
		super(file, csn);
		// TODO Auto-generated constructor stub
	}

	private TracePrintStream(File file) throws FileNotFoundException {
		super(file);
		// TODO Auto-generated constructor stub
	}

	private TracePrintStream(OutputStream out, boolean autoFlush, String encoding)
			throws UnsupportedEncodingException {
		super(out, autoFlush, encoding);
		// TODO Auto-generated constructor stub
	}

	private TracePrintStream(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
		// TODO Auto-generated constructor stub
	}

	private TracePrintStream(OutputStream out) {
		super(out);
		// TODO Auto-generated constructor stub
	}

	private TracePrintStream(String fileName, String csn)
			throws FileNotFoundException, UnsupportedEncodingException {
		super(fileName, csn);
		// TODO Auto-generated constructor stub
	}

	private TracePrintStream(String fileName) throws FileNotFoundException {
		super(fileName);
	}

	void out(String x,boolean newLine,String printType) {
		super.out(x);
		if(newLine) { 
			super.newLine();
		}
	}
	
}
