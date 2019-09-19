package com.neo.springbootneo4j.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.springbootneo4j.model.Node;
import com.neo.springbootneo4j.repository.NodeRepository;

@RestController
@RequestMapping("/")
public class NodeController {
	
	@Autowired
	private NodeRepository nodeRepository;
	
	@GetMapping("/get-children/{nodeId}")
	public Collection<Node> getAllChildren( @PathVariable String nodeId ) {
		return nodeRepository.getAllChildren(nodeId);
	}

}
