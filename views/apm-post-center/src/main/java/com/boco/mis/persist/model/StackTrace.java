package com.boco.mis.persist.model;

import com.boco.mis.persist.elasticsearch.annotation.EsIndex;
import com.boco.mis.persist.elasticsearch.annotation.EsIndexField;

@EsIndex(indexName = "stack_trace", indexType = "default_stack")
public class StackTrace extends ApmModelIndex {

	static final String INDEX_NAME = "stack_trace";
	static final String INDEX_TYPE = "default_stack";
	
	public StackTrace() {
		// TODO Auto-generated constructor stub
	}

	@EsIndexField(fieldName = "id", fieldType = "keyword")
	private String id;
	
	@EsIndexField(fieldType = "keyword")
	private String ref;
	
	@EsIndexField(fieldName = "text", fieldType = "text")
	private String text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
