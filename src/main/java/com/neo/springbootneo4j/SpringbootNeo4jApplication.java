package com.neo.springbootneo4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.neo.springbootneo4j.model.Node;
import com.neo.springbootneo4j.repository.NodeRepository;

@SpringBootApplication
public class SpringbootNeo4jApplication {

	@Autowired
	private NodeRepository nodeRepository;

	public static void main(String[] args) {

		SpringApplication.run(SpringbootNeo4jApplication.class, args);

	}

	/**
	 * This method will load the Json file once the application is up
	 * 
	 */
	@PostConstruct
	private void init() {
		
		ClassLoader classLoader = new SpringbootNeo4jApplication().getClass().getClassLoader();
		
		try {

			//Reading huge JSON file in stream mode with GSON JsonReader
			JsonReader reader = new JsonReader(new FileReader(classLoader.getResource("sample.json").getFile()));

			Gson gson = new GsonBuilder().create();

			Node node = null;

			reader.beginArray();
			// Read file in stream mode
			while (reader.hasNext()) {

				// Read data into object model
				node = gson.fromJson(reader, Node.class);

				Optional<Node> parentNode = null;

				//Checking if the node has parent
				if ( !node.getParentNode().equals("0") ) {
					
					//find the parent node with id
					parentNode = nodeRepository.findById(node.getParentNode(), 0);
					
					//setting the node as child of the parent
					parentNode.get().setChild(node);

				} else {

					parentNode = Optional.of(node);
				}

				//persisting data to neo4j
				nodeRepository.save(parentNode.get());
			}
			reader.endArray();
			
			reader.close();

		} catch (FileNotFoundException e) {
			
			System.out.println("Json file not found");
			
		} catch (IOException e) {
			
			System.out.println("Exception occured.");
			
		}
	}
}
