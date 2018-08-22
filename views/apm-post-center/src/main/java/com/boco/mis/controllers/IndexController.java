package com.boco.mis.controllers;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sun.misc.Contended;

import com.boco.mis.gzip.ZipUtil;
import com.boco.mis.services.IApmdataService;

@RestController
@RequestMapping("/")
public class IndexController {

	@Resource
	private IApmdataService apmdataService;
	
	public IndexController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 测试连接
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/connect", method = RequestMethod.HEAD)
	public String connect() {
		return "success";
	}
	
	@PostMapping(value="/trace",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public String trace(String traceSourceData) {
		System.out.println(traceSourceData);
		try {
			System.out.println(ZipUtil.uncompress(traceSourceData));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// json格式的字符串
		return "success";
	}
}
