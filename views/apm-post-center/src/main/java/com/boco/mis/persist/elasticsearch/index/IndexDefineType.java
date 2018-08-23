package com.boco.mis.persist.elasticsearch.index;

import java.util.HashMap;
import java.util.Map;

public class IndexDefineType {

	private Map<String,IndexDefineField> properties;

	public IndexDefineType() {
		properties = new HashMap<String,IndexDefineField>();
	}
	
	public Map<String, IndexDefineField> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, IndexDefineField> properties) {
		this.properties = properties;
	}
	
}
