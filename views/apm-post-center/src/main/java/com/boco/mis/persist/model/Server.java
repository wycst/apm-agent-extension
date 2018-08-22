package com.boco.mis.persist.model;

import com.boco.mis.persist.elasticsearch.annotation.EsIndex;
import com.boco.mis.persist.elasticsearch.annotation.EsIndexField;

@EsIndex(indexName = Server.INDEX_NAME, indexType = Server.INDEX_TYPE)
public class Server extends ApmModelIndex {

	static final String INDEX_NAME = "servers";
	static final String INDEX_TYPE = "default_server";
	
	public Server() {
	}
	
	@EsIndexField(fieldType = "keyword")
	private String id;
	
	@EsIndexField(fieldType = "keyword")
	private String name;
	
	@EsIndexField(fieldType = "keyword")
	private String host;
	
	@EsIndexField(fieldType = "keyword")
	private String port;

	@EsIndexField(fieldType = "keyword")
	private String type;
	
	@EsIndexField(fieldType = "keyword")
	private String category;
	
	@EsIndexField(fieldType = "keyword")
	private String version;
	
	@EsIndexField(fieldType = "keyword")
	private String url;

	@EsIndexField(fieldType = "keyword")
	private String user;
	
	@EsIndexField(fieldType = "keyword")
	private String hostPortPair;

	// 开始毫秒数
	@EsIndexField(fieldType = "long")
	private long begin;

	// 结束毫秒数
	@EsIndexField(fieldType = "long")
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

	public String indexName() {
		return INDEX_NAME;
	}

	public String indexType() {
		return INDEX_TYPE;
	}

	public String indexId() {
		return id;
	}
	

}
