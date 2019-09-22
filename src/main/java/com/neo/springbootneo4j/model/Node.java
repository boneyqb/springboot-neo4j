package com.neo.springbootneo4j.model;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NodeEntity
@AllArgsConstructor
@NoArgsConstructor
public class Node {
	
	@Id
	@SerializedName("node-id")
	@ApiModelProperty (notes = "Id of the node")
	private String nodeId;
	
	@ApiModelProperty (notes = "Name of node")
	private String name;
	
	@SerializedName("parent-node")
	private String parentNode;
	
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

}
