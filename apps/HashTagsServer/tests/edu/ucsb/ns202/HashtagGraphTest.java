package edu.ucsb.ns202;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import org.json.JSONArray;

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
	public void testGetNodes() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		assertEquals(0, hashtagGraph.getNodes().size());
		hashtagGraph.addNode("noDe1");
		assertArrayEquals(new String[]{"noDe1"}, hashtagGraph.getNodes().toArray());
		hashtagGraph.addEgde("NodE2", "NODe3");
		assertArrayEquals(new String[]{"noDe1", "NodE2", "NODe3"}, hashtagGraph.getNodes().toArray());
		hashtagGraph.addEgde("NodE1", "NODe4");
		assertArrayEquals(new String[]{"noDe1", "NODe4", "NodE2", "NODe3"}, hashtagGraph.getNodes().toArray());
		hashtagGraph.addNode("noDe4");
		assertArrayEquals(new String[]{"noDe1", "NODe4", "NodE2", "NODe3"}, hashtagGraph.getNodes().toArray());
		hashtagGraph.addNode("noDe5");
		assertArrayEquals(new String[]{"noDe1", "NODe4", "noDe5", "NodE2", "NODe3"}, hashtagGraph.getNodes().toArray());
		
		hashtagGraph.getNodes().remove("NODe4");
		assertArrayEquals(new String[]{"noDe1", "NODe4", "noDe5", "NodE2", "NODe3"}, hashtagGraph.getNodes().toArray());
	}
	
	@Test
	public void testGetNodesAsJSON() {
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		hashtagGraph.addNode("noDe1");
		hashtagGraph.addNode("noDE2");
		hashtagGraph.addNode("NODe3");
		hashtagGraph.addNode("node4");
		
		assertEquals(4, hashtagGraph.getNodesAsJSON().length());
		assertEquals("[\"{\\\"index\\\":0,\\\"name\\\":\\\"noDe1\\\"}\",\"{\\\"index\\\":3,\\\"name\\\":\\\"node4\\\"}\",\"{\\\"index\\\":1,\\\"name\\\":\\\"noDE2\\\"}\",\"{\\\"index\\\":2,\\\"name\\\":\\\"NODe3\\\"}\"]", hashtagGraph.getNodesAsJSON().toString());
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
		assertEquals("[\"{\\\"source\\\":0,\\\"target\\\":1}\",\"{\\\"source\\\":0,\\\"target\\\":2}\",\"{\\\"source\\\":0,\\\"target\\\":3}\",\"{\\\"source\\\":1,\\\"target\\\":2}\",\"{\\\"source\\\":2,\\\"target\\\":3}\"]", hashtagGraph.getEdgesAsJSON().toString());
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
		
		String expected = "{\"nodes\":\"[\\\"{\\\\\\\"index\\\\\\\":0,\\\\\\\"name\\\\\\\":\\\\\\\"noDe1\\\\\\\"}\\\",\\\"{\\\\\\\"index\\\\\\\":3,\\\\\\\"name\\\\\\\":\\\\\\\"node4\\\\\\\"}\\\",\\\"{\\\\\\\"index\\\\\\\":1,\\\\\\\"name\\\\\\\":\\\\\\\"noDE2\\\\\\\"}\\\",\\\"{\\\\\\\"index\\\\\\\":2,\\\\\\\"name\\\\\\\":\\\\\\\"NODe3\\\\\\\"}\\\"]\",";
		expected += "\"links\":\"[\\\"{\\\\\\\"source\\\\\\\":0,\\\\\\\"target\\\\\\\":1}\\\",\\\"{\\\\\\\"source\\\\\\\":0,\\\\\\\"target\\\\\\\":2}\\\",\\\"{\\\\\\\"source\\\\\\\":0,\\\\\\\"target\\\\\\\":3}\\\",\\\"{\\\\\\\"source\\\\\\\":1,\\\\\\\"target\\\\\\\":2}\\\",\\\"{\\\\\\\"source\\\\\\\":2,\\\\\\\"target\\\\\\\":3}\\\"]\"}";
		assertEquals(expected, hashtagGraph.getNodesAndEdgesAsJSON().toString());
	}
}
