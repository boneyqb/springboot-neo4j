package com.neo.springbootneo4j.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

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

@RestController
@RequestMapping("/")
public class NodeController {

	@Autowired
	private NodeRepository nodeRepository;
	
	@GetMapping("/get-children/{nodeId}")
	public Collection<Node> getAllChildren( @PathVariable String nodeId ) {
		return nodeRepository.getAllChildren(nodeId);
	}

	@GetMapping("/update-parent/{childId}/{newParentId}")
	public String updateParent( @PathVariable String childId, @PathVariable String newParentId ) {
		nodeRepository.updateParent( childId, newParentId );
		return "Relationship successfully updated.";
	}

	
	/**
	 * This method will load the Json file from resources
	 * 
	 */
	@GetMapping("/load-json")
	public String loadJson() {
		ClassLoader classLoader = new SpringbootNeo4jApplication().getClass().getClassLoader();

		try {
			
			//Reading huge JSON file in stream mode with GSON JsonReader
			JsonReader reader = new JsonReader(new InputStreamReader(classLoader.getResourceAsStream("sample.json"), "UTF-8"));
			
			System.out.println("====> "+reader);

			Gson gson = new GsonBuilder().create();

			Node node = null;

			reader.beginArray();
			// Read file in stream mode
			while (reader.hasNext()) {

				// Read data into object model
				node = gson.fromJson(reader, Node.class);

				//persist node
				nodeRepository.save(node);

				//Checking if the node has parent
				if ( StringUtils.hasText(node.getParentNode()) && !node.getParentNode().equals("0") ) {

					//Create relationship with the parent
					nodeRepository.createRelationship(node.getNodeId(), node.getParentNode());
				}

			}
			reader.endArray();

			reader.close();

			//This will create a index in the database with Id
			nodeRepository.createIndexWithId();

		} catch (FileNotFoundException e) {

			System.out.println("Json file not found");

		} catch (IOException e) {

			System.out.println("Exception occured.");

		}
		return "Successfully loaded.";
	}

}
