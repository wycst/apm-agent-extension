package com.boco.mis.persist.elasticsearch.index;

import java.util.HashMap;
import java.util.Map;

public class IndexDefineSettings {

	private Map<String,Object> index ;
	
	public IndexDefineSettings() {
		index = new HashMap<String,Object>();
	}

	public Map<String, Object> getIndex() {
		return index;
	}

	public void setIndex(Map<String, Object> index) {
		this.index = index;
	}

}
