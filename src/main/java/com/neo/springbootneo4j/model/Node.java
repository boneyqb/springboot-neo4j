package com.neo.springbootneo4j.model;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NodeEntity
@AllArgsConstructor
@NoArgsConstructor
public class Node {
	
	@Id
	@SerializedName("node-id")
	private String nodeId;
	
	private String name;
	
	@SerializedName("parent-node")
	private String parentNode;
	
	private String height;
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentNode() {
		return parentNode;
	}

	public void setParentNode(String parentNode) {
		this.parentNode = parentNode;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
}
