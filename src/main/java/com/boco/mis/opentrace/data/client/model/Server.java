package com.boco.mis.opentrace.data.client.model;

import java.util.UUID;

public class Server {

	
	public Server() {
		this.id = UUID.randomUUID().toString();
	}
	
	private String id;
	
	private String traceId;
	
	private String name;
	
	private String host;
	
	
	private String port;

	
	private String type;
	
	
	private String category;
	
	
	private String version;
	
	
	private String url;

	
	private String user;
	
	
	private String hostPortPair;

	// 开始毫秒数
	private long begin;

	// 结束毫秒数
	private long end;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getHostPortPair() {
		return hostPortPair;
	}

	public void setHostPortPair(String hostPortPair) {
		this.hostPortPair = hostPortPair;
	}

	public long getBegin() {
		return begin;
	}

	public void setBegin(long begin) {
		this.begin = begin;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	
	
}
