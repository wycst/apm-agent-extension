package com.boco.mis.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boco.mis.persist.elasticsearch.client.IElasticsearchRestClient;

@RestController
@RequestMapping("/")
public class ElasticsearchController {

	private IElasticsearchRestClient elasticsearchRestClient;
	
	@GetMapping("/initIndexs")
	public String initIndexs(String msg) {
		elasticsearchRestClient.initApmIndexs();
		return " indexs init success !";
	}
	
}
