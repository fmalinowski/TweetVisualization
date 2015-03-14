package edu.ucsb.ns202.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.json.JSONArray;

import edu.ucsb.ns202.graph.HashtagGraph;

public class HashtagGraphTest {

	@Test
	public void testAddNodeAndHasNode() {
		HashtagNode node1, node2;
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		assertFalse(hashtagGraph.hasNode("#HASHTAG1"));
		assertFalse(hashtagGraph.hasNode("#hashTag2"));	
		assertEquals(0, hashtagGraph.getTotalNumberOfTweets());
		
		hashtagGraph.addNode("#hashTag1");
		node1 = hashtagGraph.getHashtagNode("#hashTag1");
		
		assertTrue(hashtagGraph.hasNode("#HASHTAG1"));
		assertEquals(1, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(1, node1.getTotalTweetNumber());
		assertEquals(1, node1.getTotalTweetNumberWithOneHashtag());
		assertFalse(hashtagGraph.hasNode("#hashTag2"));
		
		hashtagGraph.addNode("#HaShtAG2");
		node2 = hashtagGraph.getHashtagNode("#HaShtAG2");
		
		assertTrue(hashtagGraph.hasNode("#HASHTAG1"));
		assertTrue(hashtagGraph.hasNode("#hashTag2"));
		assertEquals(2, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(1, node1.getTotalTweetNumber());
		assertEquals(1, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node2.getTotalTweetNumber());
		assertEquals(1, node2.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.addNode("#hashTag1");
		assertEquals(3, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(2, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node2.getTotalTweetNumber());
		assertEquals(1, node2.getTotalTweetNumberWithOneHashtag());
	}
	
	@Test
	public void testIncrementNodeWeight() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		HashtagNode node1, node2;
		
		assertFalse(hashtagGraph.hasNode("#HASHTAG1"));
		hashtagGraph.incrementNodeWeight("#hashTag1");
		assertEquals(0, hashtagGraph.getTotalNumberOfTweets());
		
		hashtagGraph.addNode("#HASHTAG1");
		node1 = hashtagGraph.getHashtagNode("#HASHTAG1");
		assertEquals(1, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(1, node1.getTotalTweetNumber());
		assertEquals(1, node1.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.incrementNodeWeight("#hashTag1");
		assertEquals(2, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(2, node1.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.addNode("#HASHTAG2");
		node2 = hashtagGraph.getHashtagNode("#HASHTAG2");
		assertEquals(3, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(2, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node2.getTotalTweetNumber());
		assertEquals(1, node2.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.incrementNodeWeight("#hashTag1");
		assertEquals(4, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(3, node1.getTotalTweetNumber());
		assertEquals(3, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node2.getTotalTweetNumber());
		assertEquals(1, node2.getTotalTweetNumberWithOneHashtag());
	}

	@Test
	public void testAddEdgeAndHasEdge() {
		HashtagNode node1, node2, node3, node5, node6;
		HashtagEdge edge1, edge2, edge3;
		
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		hashtagGraph.addNode("Node1");
		hashtagGraph.addNode("NoDe2");
		hashtagGraph.addNode("NodE3");
		hashtagGraph.addNode("NOde4");
		assertEquals(4, hashtagGraph.getTotalNumberOfTweets());
		
		node1 = hashtagGraph.getHashtagNode("Node1");
		node2 = hashtagGraph.getHashtagNode("Node2");
		node3 = hashtagGraph.getHashtagNode("Node3");
		
		assertFalse(hashtagGraph.hasEdge("Node1", "NoDe2"));
		hashtagGraph.addEdge("NodE1", "NOde2");
		edge1 = hashtagGraph.getHashtagEdge("Node1", "Node2");
		assertTrue(hashtagGraph.hasEdge("node1", "node2"));
		assertTrue(hashtagGraph.hasEdge("node2", "node1"));
		assertEquals(5, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(1, edge1.getNumberOfTweets());
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(1, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(2, node2.getTotalTweetNumber());
		assertEquals(1, node2.getTotalTweetNumberWithOneHashtag());
		
		assertFalse(hashtagGraph.hasEdge("node1", "node3"));
		hashtagGraph.addEdge("Node1", "Node3");
		edge2 = hashtagGraph.getHashtagEdge("node1", "node3");
		assertTrue(hashtagGraph.hasEdge("node1", "node3"));
		assertTrue(hashtagGraph.hasEdge("node3", "node1"));
		assertEquals(6, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(1, edge2.getNumberOfTweets());
		assertEquals(3, node1.getTotalTweetNumber());
		assertEquals(1, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(2, node3.getTotalTweetNumber());
		assertEquals(1, node3.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.addEdge("Node1", "Node3");
		assertEquals(7, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(2, edge2.getNumberOfTweets());
		assertEquals(4, node1.getTotalTweetNumber());
		assertEquals(1, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(3, node3.getTotalTweetNumber());
		assertEquals(1, node3.getTotalTweetNumberWithOneHashtag());	
		
		assertFalse(hashtagGraph.hasEdge("Node1", "Node1"));
		hashtagGraph.addEdge("Node1", "Node1");
		assertFalse(hashtagGraph.hasEdge("Node1", "Node1"));
		assertEquals(7, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(1, edge1.getNumberOfTweets());
		assertEquals(4, node1.getTotalTweetNumber());
		assertEquals(1, node1.getTotalTweetNumberWithOneHashtag());
		
		assertFalse(hashtagGraph.hasEdge("Node5", "Node6"));
		hashtagGraph.addEdge("Node5", "Node6");
		edge3 = hashtagGraph.getHashtagEdge("node5", "node6");
		node5 = hashtagGraph.getHashtagNode("node5");
		node6 = hashtagGraph.getHashtagNode("node6");
		assertTrue(hashtagGraph.hasEdge("node5", "node6"));
		assertEquals(8, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(1, edge3.getNumberOfTweets());
		assertEquals(1, node5.getTotalTweetNumber());
		assertEquals(0, node5.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node6.getTotalTweetNumber());
		assertEquals(0, node6.getTotalTweetNumberWithOneHashtag());
	}
	
	@Test
	public void testIncrementEdgeWeight() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		HashtagEdge edge1, edge2;
		HashtagNode node1, node2, node3;
		
		hashtagGraph.incrementEdgeWeight("node1", "node2");
		assertEquals(0, hashtagGraph.getTotalNumberOfTweets());
		
		hashtagGraph.addEdge("node1", "node2");
		edge1 = hashtagGraph.getHashtagEdge("node1", "node2");
		node1 = hashtagGraph.getHashtagNode("node1");
		node2 = hashtagGraph.getHashtagNode("node2");
		
		hashtagGraph.incrementEdgeWeight("noDe1", "Node2");
		assertEquals(2, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(2, edge1.getNumberOfTweets());
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(2, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.addEdge("node2", "node3");
		edge2 = hashtagGraph.getHashtagEdge("node2", "node3");
		node3 = hashtagGraph.getHashtagNode("node3");
		hashtagGraph.incrementEdgeWeight("noDe2", "Node3");
		assertEquals(4, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(2, edge2.getNumberOfTweets());
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(4, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		assertEquals(2, node3.getTotalTweetNumber());
		assertEquals(0, node3.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.incrementEdgeWeight("noDe1", "Node2");
		assertEquals(5, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(3, edge1.getNumberOfTweets());
		assertEquals(2, edge2.getNumberOfTweets());
		assertEquals(3, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(5, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		assertEquals(2, node3.getTotalTweetNumber());
		assertEquals(0, node3.getTotalTweetNumberWithOneHashtag());
	}
	
	@Test
	public void testGetCountOfEdgesFromSource() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		assertEquals(0, hashtagGraph.getCountOfEdgesFromSource("node1"));
		assertEquals(0, hashtagGraph.getCountOfEdgesFromSource("node2"));
		assertEquals(0, hashtagGraph.getCountOfEdgesFromSource("node3"));
		assertEquals(0, hashtagGraph.getCountOfEdgesFromSource("node4"));
		assertEquals(0, hashtagGraph.getCountOfEdgesFromSource("node5"));
		
		hashtagGraph.addNode("node1");
		assertEquals(0, hashtagGraph.getCountOfEdgesFromSource("node1"));
		
		hashtagGraph.addEdge("node1", "node2");
		assertEquals(1, hashtagGraph.getCountOfEdgesFromSource("node1"));
		assertEquals(1, hashtagGraph.getCountOfEdgesFromSource("node2"));
		
		hashtagGraph.addEdge("node1", "node3");
		assertEquals(2, hashtagGraph.getCountOfEdgesFromSource("node1"));
		assertEquals(1, hashtagGraph.getCountOfEdgesFromSource("node3"));
		
		hashtagGraph.addEdge("node4", "node1");
		assertEquals(3, hashtagGraph.getCountOfEdgesFromSource("node1"));
		assertEquals(1, hashtagGraph.getCountOfEdgesFromSource("node4"));
		
		hashtagGraph.addEdge("node2", "node3");
		assertEquals(2, hashtagGraph.getCountOfEdgesFromSource("node2"));
		assertEquals(2, hashtagGraph.getCountOfEdgesFromSource("node3"));
		
		hashtagGraph.addEdge("node4", "node5");
		assertEquals(2, hashtagGraph.getCountOfEdgesFromSource("node4"));
		assertEquals(1, hashtagGraph.getCountOfEdgesFromSource("node5"));
		
		hashtagGraph.addEdge("node6", "node4");
		assertEquals(3, hashtagGraph.getCountOfEdgesFromSource("node4"));
		assertEquals(1, hashtagGraph.getCountOfEdgesFromSource("node6"));
	}
	
	@Test
	public void testGetCountOfNodes() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		assertEquals(0, hashtagGraph.getCountOfNodes());
		hashtagGraph.addNode("node1");
		assertEquals(1, hashtagGraph.getCountOfNodes());
		hashtagGraph.addNode("node2");
		assertEquals(2, hashtagGraph.getCountOfNodes());
		hashtagGraph.addEdge("node2", "node3");
		assertEquals(3, hashtagGraph.getCountOfNodes());
		hashtagGraph.addEdge("node4", "node5");
		assertEquals(5, hashtagGraph.getCountOfNodes());
		hashtagGraph.addEdge("node4", "node4");
		assertEquals(5, hashtagGraph.getCountOfNodes());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetNodeStringList() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		assertEquals(0, hashtagGraph.getNodeStringList().size());
		hashtagGraph.addNode("noDe1");
		assertArrayEquals(new String[]{"noDe1"}, hashtagGraph.getNodeStringList().toArray());
		hashtagGraph.addEdge("NodE2", "NODe3");
		assertArrayEquals(new String[]{"noDe1", "NodE2", "NODe3"}, hashtagGraph.getNodeStringList().toArray());
		hashtagGraph.addEdge("NodE1", "NODe4");
		assertArrayEquals(new String[]{"noDe1", "NodE2", "NODe3", "NODe4"}, hashtagGraph.getNodeStringList().toArray());
		hashtagGraph.addNode("noDe4");
		assertArrayEquals(new String[]{"noDe1", "NodE2", "NODe3", "NODe4"}, hashtagGraph.getNodeStringList().toArray());
		hashtagGraph.addNode("noDe5");
		assertArrayEquals(new String[]{"noDe1", "NodE2", "NODe3", "NODe4", "noDe5"}, hashtagGraph.getNodeStringList().toArray());
		
		hashtagGraph.getNodes().remove("NODe4");
		assertArrayEquals(new String[]{"noDe1", "NodE2", "NODe3", "NODe4", "noDe5"}, hashtagGraph.getNodeStringList().toArray());
	}
	
	@Test
	public void testGetNodesAsJSON() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		hashtagGraph.addNode("noDe1");
		hashtagGraph.addNode("noDE2");
		hashtagGraph.addNode("NODe3");
		hashtagGraph.addNode("node4");
		
		assertEquals(4, hashtagGraph.getNodesAsJSON().length());
		assertEquals("[{\"id\":0,\"name\":\"noDe1\",\"radius\":1},{\"id\":1,\"name\":\"noDE2\",\"radius\":1},{\"id\":2,\"name\":\"NODe3\",\"radius\":1},{\"id\":3,\"name\":\"node4\",\"radius\":1}]", hashtagGraph.getNodesAsJSON().toString());
	}
	
	@Test
	public void testGetEdgesAsJSON() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		hashtagGraph.addNode("noDe1");
		hashtagGraph.addNode("noDE2");
		hashtagGraph.addNode("NODe3");
		hashtagGraph.addNode("node4");
		
		hashtagGraph.addEdge("node1", "node2");
		hashtagGraph.addEdge("node1", "node3");
		hashtagGraph.addEdge("node1", "node4");
		hashtagGraph.addEdge("node3", "node2");
		hashtagGraph.addEdge("node3", "node4");
		
		assertEquals(5, hashtagGraph.getEdgesAsJSON().length());
		assertEquals("[{\"weight\":0.81,\"source\":0,\"target\":1},{\"weight\":0.81,\"source\":0,\"target\":2},{\"weight\":0.81,\"source\":0,\"target\":3},{\"weight\":0.81,\"source\":1,\"target\":2},{\"weight\":0.81,\"source\":2,\"target\":3}]", hashtagGraph.getEdgesAsJSON().toString());
	}
	
	@Test
	public void testGetNodesAndEdgesAsJSON() {
		HashtagGraph hashtagGraph = new HashtagGraph();

		hashtagGraph.addNode("noDe1");
		hashtagGraph.addNode("noDE2");
		hashtagGraph.addNode("NODe3");
		hashtagGraph.addNode("node4");
		
		hashtagGraph.addEdge("node1", "node2");
		hashtagGraph.addEdge("node1", "node3");
		hashtagGraph.addEdge("node1", "node4");
		hashtagGraph.addEdge("node3", "node2");
		hashtagGraph.addEdge("node3", "node4");
		
		String expected = "{\"nodes\":[{\"id\":0,\"name\":\"noDe1\",\"radius\":6.25},{\"id\":1,\"name\":\"noDE2\",\"radius\":4.5},{\"id\":2,\"name\":\"NODe3\",\"radius\":6.25},{\"id\":3,\"name\":\"node4\",\"radius\":4.5}],";
		expected += "\"links\":[{\"weight\":0.81,\"source\":0,\"target\":1},{\"weight\":0.81,\"source\":0,\"target\":2},{\"weight\":0.81,\"source\":0,\"target\":3},{\"weight\":0.81,\"source\":1,\"target\":2},{\"weight\":0.81,\"source\":2,\"target\":3}]}";
		assertEquals(expected, hashtagGraph.getNodesAndEdgesAsJSON().toString());
	}
	
	@Test
	public void testGetHashtagNode() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		HashtagNode node1, node2, node3;
		
		hashtagGraph.addNode("noDe1");
		hashtagGraph.addNode("noDe1");
		hashtagGraph.addNode("noDe2");
		hashtagGraph.addEdge("noDe2", "noDe3");
		
		node1 = hashtagGraph.getHashtagNode("noDe1");
		node2 = hashtagGraph.getHashtagNode("noDe2");
		node3 = hashtagGraph.getHashtagNode("noDe3");
		
		assertEquals("noDe1", node1.getNameWithCase());
		assertEquals("noDe2", node2.getNameWithCase());
		assertEquals("noDe3", node3.getNameWithCase());
		
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(2, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(2, node2.getTotalTweetNumber());
		assertEquals(1, node2.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node3.getTotalTweetNumber());
		assertEquals(0, node3.getTotalTweetNumberWithOneHashtag());
	}
	
	@Test
	public void testGetHashtagEdge() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		HashtagNode node1, node2, node3;
		HashtagEdge edge1, edge2;
		
		hashtagGraph.addEdge("noDe1", "noDe2");
		hashtagGraph.addEdge("noDe1", "noDe2");
		hashtagGraph.addEdge("noDe2", "noDe3");
		
		node1 = hashtagGraph.getHashtagNode("noDe1");
		node2 = hashtagGraph.getHashtagNode("noDe2");
		node3 = hashtagGraph.getHashtagNode("noDe3");
		edge1 = hashtagGraph.getHashtagEdge("noDe1", "noDe2");
		edge2 = hashtagGraph.getHashtagEdge("noDe2", "noDe3");
		
		assertEquals(node1, edge1.getSource());
		assertEquals(node2, edge1.getTarget());
		assertEquals(node2, edge2.getSource());
		assertEquals(node3, edge2.getTarget());
		
		assertEquals(2, edge1.getNumberOfTweets());
		assertEquals(1, edge2.getNumberOfTweets());
	}
	
	@Test
	public void testGetNodes() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		HashtagNode node1, node2, node3;
		ArrayList<HashtagNode> hashtagNodeList;
		
		hashtagGraph.addNode("node1");
		hashtagGraph.addNode("node2");
		hashtagGraph.addNode("node3");
		
		node1 = hashtagGraph.getHashtagNode("node1");
		node2 = hashtagGraph.getHashtagNode("node2");
		node3 = hashtagGraph.getHashtagNode("node3");
		
		hashtagNodeList = hashtagGraph.getNodes();
		assertEquals(3, hashtagNodeList.size());
		assertEquals(node1, hashtagNodeList.get(0));
		assertEquals(node2, hashtagNodeList.get(1));
		assertEquals(node3, hashtagNodeList.get(2));
	}
	
	@Test
	public void testComputeD3NodeRadius() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		HashtagNode node1, node3;
		
		hashtagGraph.addNode("node1");
		hashtagGraph.addNode("node1");
		hashtagGraph.addNode("node1");
		hashtagGraph.addNode("node2");
		hashtagGraph.addNode("node3");
		hashtagGraph.addNode("node3");
		
		node1 = hashtagGraph.getHashtagNode("node1");
		node3 = hashtagGraph.getHashtagNode("node3");
		
		assertEquals(6.61, hashtagGraph.computeD3NodeRadius(node1), 0.00001);
		assertEquals(3.8, hashtagGraph.computeD3NodeRadius(node3), 0.00001);
	}
	
	@Test
	public void testComputeD3EdgeWeight() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		HashtagEdge edge1, edge2, edge3;
		
		hashtagGraph.addEdge("node1", "node2");
		hashtagGraph.addEdge("node1", "node2");
		hashtagGraph.addEdge("node1", "node2");
		hashtagGraph.addEdge("node2", "node3");
		hashtagGraph.addEdge("node3", "node4");
		hashtagGraph.addEdge("node3", "node4");
		
		edge1 = hashtagGraph.getHashtagEdge("node1", "node2");
		edge2 = hashtagGraph.getHashtagEdge("node2", "node3");
		edge3 = hashtagGraph.getHashtagEdge("node3", "node4");
		
		assertEquals(3.29, hashtagGraph.computeD3EdgeWeight(edge1), 0.00001);
		assertEquals(0.81, hashtagGraph.computeD3EdgeWeight(edge2), 0.00001);
		assertEquals(2.05, hashtagGraph.computeD3EdgeWeight(edge3), 0.00001);
	}
	
}
