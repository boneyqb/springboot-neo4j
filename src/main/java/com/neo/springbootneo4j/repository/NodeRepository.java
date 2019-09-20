package com.neo.springbootneo4j.repository;

import java.util.Collection;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.neo.springbootneo4j.model.Node;

@Repository
public interface NodeRepository extends Neo4jRepository<Node, String>{

	@Query ("Match(n:Node{nodeId:{nodeId}})-[:CHILD]->(s:Node) WITH n,s\r\n" + 
			"MATCH (r:Node) WHERE NOT (r)<-[:CHILD]-()\r\n" + 
			"WITH n,r,s Match p=(a)-[:CHILD*]-(c)\r\n" + 
			"where a.nodeId=r.nodeId and c.nodeId=s.nodeId\r\n" + 
			"return s.nodeId AS nodeId,s.nodeId AS name,length(p) AS height,n AS parent,r AS root")
	Collection<Map<?,?>> getAllChildren(String nodeId);

	@Query ("MATCH (n:Node {nodeId: {nodeId} } ), (r:Node {nodeId: {parentNode} }) MERGE (n)<-[:CHILD]-(r)")
	void createRelationship(String nodeId, String parentNode);

	@Query ( "MATCH (u:Node {nodeId : {childId} })<-[r:CHILD]-(p:Node)\r\n" + 
			"DELETE r\r\n" + 
			"WITH u MATCH (r:Node {nodeId: {newParentId} }) MERGE (u)<-[:CHILD]-(r) SET u.parentNode = r.nodeId" )
	void updateParent(String childId, String newParentId);

	@Query ("CREATE INDEX ON :Node(nodeId)")
	void createIndexWithId();

}
