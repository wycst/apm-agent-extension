package com.boco.mis.opentrace.data.trace.tree;

import java.util.ArrayList;
import java.util.List;

import com.boco.mis.opentrace.data.trace.TraceNode;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TraceTreeNode {

	private String key;
	
	private String name;
	
	private String fullName;
	
	private long timeMillis;
	
	private String traceType;
	
	@JsonProperty("children")
	private List<TraceTreeNode> items;
	
	public boolean isOpen() {
		return items != null && items.size() > 0;
	}
	
    public TraceTreeNode(TraceNode traceNode) {
    	this.name = traceNode.getClassName() + "." + traceNode.getMethodName();
    	this.fullName = traceNode.getFullMethodName();
    	this.key = traceNode.getKey();
    	this.timeMillis = traceNode.getEndTimeMillis() - traceNode.getBeginTimeMillis();
    	this.traceType = traceNode.getTraceType();
    	
    	List<TraceNode> children = traceNode.getChildren();
    	if(children.size() > 0) {
    		items = new ArrayList<TraceTreeNode>();
    		for(TraceNode child : children) {
    			TraceTreeNode item = new TraceTreeNode(child);
    			items.add(item);
    		}
    	}
    	
    }

	public List<TraceTreeNode> getItems() {
		return items;
	}

	public void setItems(List<TraceTreeNode> items) {
		this.items = items;
	}
	
	public String getName() {
		
		String value = "<span>["+key+"] " + name + "</span>";
		// <span class="layui-badge layui-bg-green" onclick="toggleHideRow('traceNodeId_{{index}}')">{{item.traceType}}</span>
		if(traceType != null) {
			value += " <span class='layui-badge layui-bg-green'>" + traceType + "</span>";
		}
		
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public long getTimeMillis() {
		return timeMillis;
	}

	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}

}
