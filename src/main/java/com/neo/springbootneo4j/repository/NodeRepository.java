package com.neo.springbootneo4j.repository;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.neo.springbootneo4j.model.Node;

@Repository
public interface NodeRepository extends Neo4jRepository<Node, String>{

	@Query ("Match(n:Node{nodeId:{nodeId}})<-[:PARENT]-(s:Node) return s")
	Collection<Node> getAllChildren(@Param("nodeId") String nodeId);

}
