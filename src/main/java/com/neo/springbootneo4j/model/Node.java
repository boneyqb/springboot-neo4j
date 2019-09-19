package com.neo.springbootneo4j.model;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

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
	
	@Relationship( direction = Relationship.INCOMING, type = "PARENT")
	private Node child;
	
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

	public Node getChild() {
		return child;
	}

	public void setChild(Node child) {
		this.child = child;
	}
	
}
