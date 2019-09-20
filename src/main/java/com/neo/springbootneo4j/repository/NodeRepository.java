package com.neo.springbootneo4j.repository;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.neo.springbootneo4j.model.Node;

@Repository
public interface NodeRepository extends Neo4jRepository<Node, String>{

	@Query ("Match(n:Node{nodeId:{nodeId}})-[:CHILD]->(s:Node) return s")
	Collection<Node> getAllChildren(@Param("nodeId") String nodeId);

	@Query ("MATCH (n:Node {nodeId: {nodeId} } ), (r:Node {nodeId: {parentNode} }) CREATE (n)<-[:CHILD]-(r)")
	void createRelationship(String nodeId, String parentNode);

	@Query ( "MATCH (n:Node) WHERE n.nodeId = {childId} WITH n \r\n" + 
			"MATCH (m:Node {nodeId: n.nodeId } )-[r:CHILD]-(p:Node {nodeId: n.parentNode} ) DELETE r\r\n" + 
			"WITH n MATCH (n:Node {nodeId: n.nodeId }), (r:Node {nodeId: {newParentId} }) CREATE (n)<-[:CHILD]-(r) SET n.parentNode = r.nodeId" )
	void updateParent(String childId, String newParentId);

	@Query ("CREATE INDEX ON :Node(nodeId)")
	void createIndexWithId();

}
