package edu.ucsb.ns202.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.json.JSONArray;

import edu.ucsb.ns202.graph.HashtagGraph;

public class HashtagGraphTest {

	@Test
	public void testAddNodeAndHasNode() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		assertFalse(hashtagGraph.hasNode("#HASHTAG1"));
		assertFalse(hashtagGraph.hasNode("#hashTag2"));
		assertFalse(hashtagGraph.hasNode("@duDe"));
		assertFalse(hashtagGraph.hasNode("don't care"));
		
		hashtagGraph.addNode("#hashTag1");
		
		assertTrue(hashtagGraph.hasNode("#HASHTAG1"));
		assertFalse(hashtagGraph.hasNode("#hashTag2"));
		assertFalse(hashtagGraph.hasNode("@duDe"));
		assertFalse(hashtagGraph.hasNode("don't care"));
		
		hashtagGraph.addNode("#HaShtAG2");
		
		assertTrue(hashtagGraph.hasNode("#HASHTAG1"));
		assertTrue(hashtagGraph.hasNode("#hashTag2"));
		assertFalse(hashtagGraph.hasNode("@duDe"));
		assertFalse(hashtagGraph.hasNode("don't care"));
		
		hashtagGraph.addNode("@dude");
		
		assertTrue(hashtagGraph.hasNode("#HASHTAG1"));
		assertTrue(hashtagGraph.hasNode("#hashTag2"));
		assertTrue(hashtagGraph.hasNode("@duDe"));
		assertFalse(hashtagGraph.hasNode("don't care"));
		
		hashtagGraph.addNode("don't care");
		
		assertTrue(hashtagGraph.hasNode("#HASHTAG1"));
		assertTrue(hashtagGraph.hasNode("#hashTag2"));
		assertTrue(hashtagGraph.hasNode("@duDe"));
		assertTrue(hashtagGraph.hasNode("don't care"));
	}
	
	@Test
	public void testAddNodeWithIncrementOfWeight() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		HashtagNode node1, node2;
		
		assertFalse(hashtagGraph.hasNode("#HASHTAG1"));
		
		hashtagGraph.addNode("#HASHTAG1", false);
		assertTrue(hashtagGraph.hasNode("#HASHTAG1"));
		node1 = hashtagGraph.getHashtagNode("#HASHTAG1");
		assertEquals(0, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(0, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.addNode("#HASHTAG1", false);
		assertEquals(0, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(0, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.addNode("#HASHTAG1", true);
		assertEquals(1, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(1, node1.getTotalTweetNumber());
		assertEquals(1, node1.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.addNode("#HASHTAG1", true);
		assertEquals(2, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(2, node1.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.addNode("#HASHTAG2", true);
		assertTrue(hashtagGraph.hasNode("#HASHTAG2"));
		node2 = hashtagGraph.getHashtagNode("#HASHTAG2");
		assertEquals(3, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(1, node2.getTotalTweetNumber());
		assertEquals(1, node2.getTotalTweetNumberWithOneHashtag());
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(2, node1.getTotalTweetNumberWithOneHashtag());
	}

	@Test
	public void testAddEdgeAndHasEdge() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		hashtagGraph.addNode("#hashTag1");
		hashtagGraph.addNode("#HaShtAG2");
		hashtagGraph.addNode("@dude");
		hashtagGraph.addNode("don't care");
		
		assertFalse(hashtagGraph.hasEdge("#HASHTAG1", "#Hashtag2"));
		hashtagGraph.addEgde("#hashtag1", "#hashtag2");
		assertTrue(hashtagGraph.hasEdge("#HASHTAG1", "#Hashtag2"));
		assertTrue(hashtagGraph.hasEdge("#Hashtag2", "#HASHTAG1"));
		
		assertFalse(hashtagGraph.hasEdge("#hashTag1", "@dude"));
		hashtagGraph.addEgde("#hashTag1", "@dude");
		assertTrue(hashtagGraph.hasEdge("#hashTag1", "@dude"));
		assertTrue(hashtagGraph.hasEdge("@dude", "#hashTag1"));
		
		assertFalse(hashtagGraph.hasEdge("@duDe", "don't cARe"));
		hashtagGraph.addEgde("@dUDE", "DoN'T CaRE");
		assertTrue(hashtagGraph.hasEdge("@duDe", "don't cARe"));
		assertTrue(hashtagGraph.hasEdge("don't cARe", "@duDe"));
		
		assertFalse(hashtagGraph.hasEdge("@dude", "@dude"));
		hashtagGraph.addEgde("@dude", "@dude");
		assertFalse(hashtagGraph.hasEdge("@dude", "@dude"));
		
		hashtagGraph.addEgde("@dude", "@dUDe");
		assertFalse(hashtagGraph.hasEdge("@dude", "@dude"));
		assertFalse(hashtagGraph.hasEdge("@dude", "@dUDe"));
		
		assertFalse(hashtagGraph.hasEdge("@dude", "this node dos not exist before"));
		hashtagGraph.addEgde("@dude", "this node dos not exist before");
		assertTrue(hashtagGraph.hasEdge("@dude", "this node dos not exist before"));
		assertTrue(hashtagGraph.hasEdge("this node dos not exist before", "@dude"));
		assertTrue(hashtagGraph.hasEdge("@dude", "this node dos nOt EXIST before"));
		
		assertFalse(hashtagGraph.hasEdge("Inexistant Node1", "Inexistant Node2"));
		hashtagGraph.addEgde("Inexistant Node1", "Inexistant Node2");
		assertTrue(hashtagGraph.hasEdge("Inexistant Node1", "Inexistant Node2"));
		assertTrue(hashtagGraph.hasEdge("Inexistant Node2", "Inexistant Node1"));
	}
	
	@Test
	public void testAddEdgeWithIncrementOfWeight() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		HashtagEdge edge1, edge2;
		HashtagNode node1, node2, node3;
		
		assertFalse(hashtagGraph.hasEdge("node1", "node2"));
		hashtagGraph.addEgde("node1", "node2", false);
		edge1 = hashtagGraph.getHashtagEdge("node1", "node2");
		node1 = hashtagGraph.getHashtagNode("node1");
		node2 = hashtagGraph.getHashtagNode("node2");
		assertTrue(hashtagGraph.hasEdge("node1", "node2"));
		assertEquals(0, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(0, edge1.getNumberOfTweets());
		assertEquals(0, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(0, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.addEgde("node1", "node2", true);
		assertEquals(1, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(1, edge1.getNumberOfTweets());
		assertEquals(1, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		
		hashtagGraph.addEgde("node1", "node2", true);
		assertEquals(2, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(2, edge1.getNumberOfTweets());
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(2, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		
		assertFalse(hashtagGraph.hasEdge("node2", "node3"));
		hashtagGraph.addEgde("node2", "node3", true);
		edge2 = hashtagGraph.getHashtagEdge("node2", "node3");
		node3 = hashtagGraph.getHashtagNode("node3");
		assertTrue(hashtagGraph.hasEdge("node2", "node3"));
		assertEquals(3, hashtagGraph.getTotalNumberOfTweets());
		assertEquals(1, edge2.getNumberOfTweets());
		assertEquals(3, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node3.getTotalTweetNumber());
		assertEquals(0, node3.getTotalTweetNumberWithOneHashtag());
		
		assertEquals(2, edge1.getNumberOfTweets());
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
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
		
		hashtagGraph.addEgde("node1", "node2");
		assertEquals(1, hashtagGraph.getCountOfEdgesFromSource("node1"));
		assertEquals(1, hashtagGraph.getCountOfEdgesFromSource("node2"));
		
		hashtagGraph.addEgde("node1", "node3");
		assertEquals(2, hashtagGraph.getCountOfEdgesFromSource("node1"));
		assertEquals(1, hashtagGraph.getCountOfEdgesFromSource("node3"));
		
		hashtagGraph.addEgde("node4", "node1");
		assertEquals(3, hashtagGraph.getCountOfEdgesFromSource("node1"));
		assertEquals(1, hashtagGraph.getCountOfEdgesFromSource("node4"));
		
		hashtagGraph.addEgde("node2", "node3");
		assertEquals(2, hashtagGraph.getCountOfEdgesFromSource("node2"));
		assertEquals(2, hashtagGraph.getCountOfEdgesFromSource("node3"));
		
		hashtagGraph.addEgde("node4", "node5");
		assertEquals(2, hashtagGraph.getCountOfEdgesFromSource("node4"));
		assertEquals(1, hashtagGraph.getCountOfEdgesFromSource("node5"));
		
		hashtagGraph.addEgde("node6", "node4");
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
		hashtagGraph.addEgde("node2", "node3");
		assertEquals(3, hashtagGraph.getCountOfNodes());
		hashtagGraph.addEgde("node4", "node5");
		assertEquals(5, hashtagGraph.getCountOfNodes());
		hashtagGraph.addEgde("node4", "node4");
		assertEquals(5, hashtagGraph.getCountOfNodes());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetNodeStringList() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		assertEquals(0, hashtagGraph.getNodeStringList().size());
		hashtagGraph.addNode("noDe1");
		assertArrayEquals(new String[]{"noDe1"}, hashtagGraph.getNodeStringList().toArray());
		hashtagGraph.addEgde("NodE2", "NODe3");
		assertArrayEquals(new String[]{"noDe1", "NodE2", "NODe3"}, hashtagGraph.getNodeStringList().toArray());
		hashtagGraph.addEgde("NodE1", "NODe4");
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
		assertEquals("[{\"id\":0,\"name\":\"noDe1\"},{\"id\":1,\"name\":\"noDE2\"},{\"id\":2,\"name\":\"NODe3\"},{\"id\":3,\"name\":\"node4\"}]", hashtagGraph.getNodesAsJSON().toString());
	}
	
	@Test
	public void testGetEdgesAsJSON() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		hashtagGraph.addNode("noDe1");
		hashtagGraph.addNode("noDE2");
		hashtagGraph.addNode("NODe3");
		hashtagGraph.addNode("node4");
		
		hashtagGraph.addEgde("node1", "node2");
		hashtagGraph.addEgde("node1", "node3");
		hashtagGraph.addEgde("node1", "node4");
		hashtagGraph.addEgde("node3", "node2");
		hashtagGraph.addEgde("node3", "node4");
		
		assertEquals(5, hashtagGraph.getEdgesAsJSON().length());
		assertEquals("[{\"source\":0,\"target\":1},{\"source\":0,\"target\":2},{\"source\":0,\"target\":3},{\"source\":1,\"target\":2},{\"source\":2,\"target\":3}]", hashtagGraph.getEdgesAsJSON().toString());
	}
	
	@Test
	public void testGetNodesAndEdgesAsJSON() {
		HashtagGraph hashtagGraph = new HashtagGraph();

		hashtagGraph.addNode("noDe1");
		hashtagGraph.addNode("noDE2");
		hashtagGraph.addNode("NODe3");
		hashtagGraph.addNode("node4");
		
		hashtagGraph.addEgde("node1", "node2");
		hashtagGraph.addEgde("node1", "node3");
		hashtagGraph.addEgde("node1", "node4");
		hashtagGraph.addEgde("node3", "node2");
		hashtagGraph.addEgde("node3", "node4");
		
		String expected = "{\"nodes\":[{\"id\":0,\"name\":\"noDe1\"},{\"id\":1,\"name\":\"noDE2\"},{\"id\":2,\"name\":\"NODe3\"},{\"id\":3,\"name\":\"node4\"}],";
		expected += "\"links\":[{\"source\":0,\"target\":1},{\"source\":0,\"target\":2},{\"source\":0,\"target\":3},{\"source\":1,\"target\":2},{\"source\":2,\"target\":3}]}";
		assertEquals(expected, hashtagGraph.getNodesAndEdgesAsJSON().toString());
	}
	
	@Test
	public void testGetHashtagNode() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		HashtagNode node1, node2, node3;
		
		hashtagGraph.addNode("noDe1", true);
		hashtagGraph.addNode("noDe1", true);
		hashtagGraph.addNode("noDe2", true);
		hashtagGraph.addEgde("noDe2", "noDe3", true);
		
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
		
		hashtagGraph.addEgde("noDe1", "noDe2", true);
		hashtagGraph.addEgde("noDe1", "noDe2", true);
		hashtagGraph.addEgde("noDe2", "noDe3", true);
		
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
		
		hashtagGraph.addNode("node1", true);
		hashtagGraph.addNode("node1", true);
		hashtagGraph.addNode("node1", true);
		hashtagGraph.addNode("node2", true);
		hashtagGraph.addNode("node3", true);
		hashtagGraph.addNode("node3", true);
		
		node1 = hashtagGraph.getHashtagNode("node1");
		node3 = hashtagGraph.getHashtagNode("node3");
		
		assertEquals(6.61, hashtagGraph.computeD3NodeRadius(node1), 0.00001);
		assertEquals(3.8, hashtagGraph.computeD3NodeRadius(node3), 0.00001);
	}
	
	@Test
	public void testComputeD3EdgeWeight() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		HashtagEdge edge1, edge2, edge3;
		
		hashtagGraph.addEgde("node1", "node2", true);
		hashtagGraph.addEgde("node1", "node2", true);
		hashtagGraph.addEgde("node1", "node2", true);
		hashtagGraph.addEgde("node2", "node3", true);
		hashtagGraph.addEgde("node3", "node4", true);
		hashtagGraph.addEgde("node3", "node4", true);
		
		edge1 = hashtagGraph.getHashtagEdge("node1", "node2");
		edge2 = hashtagGraph.getHashtagEdge("node2", "node3");
		edge3 = hashtagGraph.getHashtagEdge("node3", "node4");
		
		assertEquals(6.61, hashtagGraph.computeD3EdgeWeight(edge1), 0.00001);
		assertEquals(6.61, hashtagGraph.computeD3EdgeWeight(edge2), 0.00001);
		assertEquals(6.61, hashtagGraph.computeD3EdgeWeight(edge3), 0.00001);
	}
	
}
