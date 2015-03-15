package edu.ucsb.ns202.graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class SortedHashtagGraphTest {

	@Test
	public void testSortEdgesByNodePopularity() {
		SortedHashtagGraph sortedHashtagGraph = new SortedHashtagGraph();
		
		sortedHashtagGraph.addEdge("node1", "node2");
		sortedHashtagGraph.addEdge("node1", "node3");
		sortedHashtagGraph.addEdge("node1", "node4");
		sortedHashtagGraph.addEdge("node1", "node5");
		
		sortedHashtagGraph.addEdge("node2", "node3");
		sortedHashtagGraph.addEdge("node2", "node4");
		sortedHashtagGraph.addEdge("node2", "node5");
		
		sortedHashtagGraph.addEdge("node6", "node7");
		
		//Weight of edge node1-node2: 2
		sortedHashtagGraph.incrementEdgeWeight("node1", "node2");
		
		//Weight of edge node1-node4: 5
		sortedHashtagGraph.incrementEdgeWeight("node1", "node4");
		sortedHashtagGraph.incrementEdgeWeight("node1", "node4");
		sortedHashtagGraph.incrementEdgeWeight("node1", "node4");
		sortedHashtagGraph.incrementEdgeWeight("node1", "node4");
		
		//Weight of edge node1-node5: 4
		sortedHashtagGraph.incrementEdgeWeight("node1", "node5");
		sortedHashtagGraph.incrementEdgeWeight("node1", "node5");
		sortedHashtagGraph.incrementEdgeWeight("node1", "node5");
		
		//Weight of edge node1-node3: 2
		sortedHashtagGraph.incrementEdgeWeight("node1", "node3");
		sortedHashtagGraph.incrementEdgeWeight("node1", "node3");
		
		//Weight of edge node2-node3: 2
		sortedHashtagGraph.incrementEdgeWeight("node2", "node3");
		
		//Weight of edge node2-node4: 4
		sortedHashtagGraph.incrementEdgeWeight("node4", "node2");
		sortedHashtagGraph.incrementEdgeWeight("node4", "node2");
		sortedHashtagGraph.incrementEdgeWeight("node4", "node2");
		
		//Weight of edge node2-node5: 3
		sortedHashtagGraph.incrementEdgeWeight("node2", "node5");
		sortedHashtagGraph.incrementEdgeWeight("node2", "node5");
		
		//Weight of edge node6-node7: 6
		sortedHashtagGraph.incrementEdgeWeight("node6", "node7");
		sortedHashtagGraph.incrementEdgeWeight("node6", "node7");
		sortedHashtagGraph.incrementEdgeWeight("node6", "node7");
		sortedHashtagGraph.incrementEdgeWeight("node6", "node7");
		sortedHashtagGraph.incrementEdgeWeight("node6", "node7");
		
		/*
		 * edge node1-node2: 2
		 * edge node1-node3: 3
		 * edge node1-node4: 5
		 * edge node1-node5: 4
		 * 
		 * edge node2-node3: 2
		 * edge node2-node4: 4
		 * edge node2-node5: 3
		 * 
		 * edge node6-node7: 6
		 * 
		 * node1: 13
		 * node2: 10
		 * node3: 5
		 * node4: 9
		 * node5: 7
		 * node6: 6
		 * node7: 6
		 */
		
		HashtagEdge hashtagEdge_1_2 = sortedHashtagGraph.getHashtagEdge("node1", "node2");
		HashtagEdge hashtagEdge_1_3 = sortedHashtagGraph.getHashtagEdge("node1", "node3");
		HashtagEdge hashtagEdge_1_4 = sortedHashtagGraph.getHashtagEdge("node1", "node4");
		HashtagEdge hashtagEdge_1_5 = sortedHashtagGraph.getHashtagEdge("node1", "node5");
		
		HashtagEdge hashtagEdge_2_1 = sortedHashtagGraph.getHashtagEdge("node2", "node1");
		HashtagEdge hashtagEdge_2_3 = sortedHashtagGraph.getHashtagEdge("node2", "node3");
		HashtagEdge hashtagEdge_2_4 = sortedHashtagGraph.getHashtagEdge("node2", "node4");
		HashtagEdge hashtagEdge_2_5 = sortedHashtagGraph.getHashtagEdge("node2", "node5");
		
		HashtagEdge hashtagEdge_3_1 = sortedHashtagGraph.getHashtagEdge("node3", "node1");
		HashtagEdge hashtagEdge_3_2 = sortedHashtagGraph.getHashtagEdge("node3", "node2");
		
//		HashtagEdge hashtagEdge_6_7 = sortedHashtagGraph.getHashtagEdge("node2", "node3");
		
		HashtagNode hashtagNode1, hashtagNode2, hashtagNode3, hashtagNode4, hashtagNode5, hashtagNode6, hashtagNode7;
		
		hashtagNode1 = sortedHashtagGraph.getHashtagNode("node1");
		hashtagNode2 = sortedHashtagGraph.getHashtagNode("node2");
		hashtagNode3 = sortedHashtagGraph.getHashtagNode("node3");
		hashtagNode4 = sortedHashtagGraph.getHashtagNode("node4");
		hashtagNode5 = sortedHashtagGraph.getHashtagNode("node5");
		hashtagNode6 = sortedHashtagGraph.getHashtagNode("node6");
		hashtagNode7 = sortedHashtagGraph.getHashtagNode("node7");
		
		sortedHashtagGraph.sortEdgesByNodePopularity();
		
		//ArrayList for 1st Node
		assertEquals(hashtagEdge_1_2, sortedHashtagGraph.getEdges("node1").get(0));
		assertEquals(hashtagEdge_1_4, sortedHashtagGraph.getEdges("node1").get(1));
		assertEquals(hashtagEdge_1_5, sortedHashtagGraph.getEdges("node1").get(2));
		assertEquals(hashtagEdge_1_3, sortedHashtagGraph.getEdges("node1").get(3));
		
		//ArrayList for 2nd Node
		assertEquals(hashtagEdge_2_1, sortedHashtagGraph.getEdges("node2").get(0));
		assertEquals(hashtagEdge_2_4, sortedHashtagGraph.getEdges("node2").get(1));
		assertEquals(hashtagEdge_2_5, sortedHashtagGraph.getEdges("node2").get(2));
		assertEquals(hashtagEdge_2_3, sortedHashtagGraph.getEdges("node2").get(3));
		
		//ArrayList for 3rd Node
		assertEquals(hashtagEdge_3_1, sortedHashtagGraph.getEdges("node3").get(0));
		assertEquals(hashtagEdge_3_2, sortedHashtagGraph.getEdges("node3").get(1));
		
		assertEquals(hashtagNode1, sortedHashtagGraph.getNodes().get(0));
		assertEquals(hashtagNode2, sortedHashtagGraph.getNodes().get(1));
		assertEquals(hashtagNode4, sortedHashtagGraph.getNodes().get(2));
		assertEquals(hashtagNode5, sortedHashtagGraph.getNodes().get(3));
		assertEquals(hashtagNode6, sortedHashtagGraph.getNodes().get(4));
		assertEquals(hashtagNode7, sortedHashtagGraph.getNodes().get(5));
		assertEquals(hashtagNode3, sortedHashtagGraph.getNodes().get(6));
		
	}

}