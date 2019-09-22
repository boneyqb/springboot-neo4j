package com.neo.springbootneo4j.controller;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.neo.springbootneo4j.SpringbootNeo4jApplication;
import com.neo.springbootneo4j.model.Node;
import com.neo.springbootneo4j.repository.NodeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Boney Varghese
 *
 */
@RestController
@RequestMapping("/node")
@Api (value = "Handles node operations for a tree structure")
public class NodeController {

	@Autowired
	private NodeRepository nodeRepository;
	
	/**
	 * This method will return the immediate child nodes for a nodeId
	 * @param nodeId
	 * @return Collection
	 */
	@ApiOperation (value = "Returns the immediate child nodes for a nodeId")
	@GetMapping ("/get-children/{nodeId}")
	public Collection<Map<?,?>> getAllChildren( @PathVariable String nodeId ) {
		return nodeRepository.getAllChildren(nodeId);
	}

	/**
	 * This method will update the parent node of a child
	 * @param childId
	 * @param newParentId
	 * @return String
	 */
	@ApiOperation (value = "Updates the parent node of a child node")
	@GetMapping ("/update-parent/{childId}/{newParentId}")
	public String updateParent( @PathVariable String childId, @PathVariable String newParentId ) {
		try {
			nodeRepository.updateParent( childId, newParentId );
			return "Relationship successfully updated.";
		} catch (Exception e) {
			return "Please check the data, please try again.";
		}
	}
	
	/**
	 * This method will return the child & sub child under a nodeId
	 * @param nodeId
	 * @return Collection
	 */
	@ApiOperation (value = "Returns the all the tree nodes under a nodeId")
	@GetMapping("/get-whole-tree/{nodeId}")
	public Collection<Map<?,?>> getAllTree( @PathVariable String nodeId ) {
		return nodeRepository.getAllTree(nodeId);
	}

	
	/**
	 * This method will load the Json file from resources
	 * 
	 */
	@ApiOperation (value = "Loads the json file from resources folder to the database")
	@GetMapping ("/load-json")
	public String loadJson() {
		ClassLoader classLoader = new SpringbootNeo4jApplication().getClass().getClassLoader();

		try {
			
			//Reading huge JSON file in stream mode with GSON JsonReader
			JsonReader reader = new JsonReader(new InputStreamReader(classLoader.getResourceAsStream("sample.json"), "UTF-8"));
			
			Gson gson = new GsonBuilder().create();

			Node node = null;

			reader.beginArray();
			// Read file in stream mode
			while (reader.hasNext()) {

				// Read data into object model
				node = gson.fromJson(reader, Node.class);
				
				//Setting the parent node id of root node as 0
				if ( !StringUtils.hasText(node.getParentNode()) ) {
					node.setParentNode("0");
				}

				//persist node
				nodeRepository.save(node);

				//Checking if the node has parent
				if ( !node.getParentNode().equals("0") ) {

					//Create relationship with the parent
					nodeRepository.createRelationship(node.getNodeId(), node.getParentNode());
				}

			}
			reader.endArray();

			reader.close();

			//This will create a index in the database with Id
			nodeRepository.createIndexWithId();

		} catch (FileNotFoundException e) {

			return "Json file not found";

		} catch (Exception e) {

			return "Please check the json file and try again.";

		}
		return "Successfully loaded.";
	}

}
