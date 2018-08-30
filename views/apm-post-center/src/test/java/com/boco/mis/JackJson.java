package com.boco.mis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.boco.mis.persist.model.ApmTraceInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JackJson {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File file = new File("d:/json.txt");
		
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");
		BufferedReader br = new BufferedReader(isr);
		
		String line = null;
		String json = "";
		
		while((line = br.readLine()) != null) {
			json += line;
		}
		
		
		isr.close();
		br.close();
		
		ObjectMapper om = new ObjectMapper();
		
		ApmTraceInfo am = om.readValue(json, ApmTraceInfo.class);
		
		System.out.println(am);
	}

}
