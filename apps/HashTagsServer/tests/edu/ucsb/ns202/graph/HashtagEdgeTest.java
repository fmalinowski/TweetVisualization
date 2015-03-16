package edu.ucsb.ns202.graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class HashtagEdgeTest {

	@Test
	public void testGetSourceAndTarget() {
		HashtagNode node1 = new HashtagNode(1, "yo");
		HashtagNode node2 = new HashtagNode(2, "wassup");
		
		HashtagEdge hashtagEdge = new HashtagEdge(node1, node2);
		assertEquals(node1, hashtagEdge.getSource());
		assertNotEquals(node2, hashtagEdge.getSource());
		assertEquals(node2, hashtagEdge.getTarget());
		assertNotEquals(node1, hashtagEdge.getTarget());
	}

	@Test
	public void testIncrementNumberOfTweets() {
		HashtagNode node1 = new HashtagNode(1, "yo");
		HashtagNode node2 = new HashtagNode(2, "wassup");
		
		HashtagEdge hashtagEdge = new HashtagEdge(node1, node2);
		assertEquals(0, hashtagEdge.getNumberOfTweetsInvolved());
		assertEquals(0, node1.getNumberOfTweetsInvolved());
		assertEquals(0, node2.getNumberOfTweetsInvolved());
		
		hashtagEdge.incrementNumberOfTweetsInvolved();
		assertEquals(1, hashtagEdge.getNumberOfTweetsInvolved());
		assertEquals(0, node1.getNumberOfTweetsInvolved());
		assertEquals(0, node2.getNumberOfTweetsInvolved());
		
		node1.incrementNumberOfTweetsInvolved();
		assertEquals(1, hashtagEdge.getNumberOfTweetsInvolved());
		assertEquals(1, node1.getNumberOfTweetsInvolved());
		assertEquals(0, node2.getNumberOfTweetsInvolved());
		
		hashtagEdge.incrementNumberOfTweetsInvolved();
		assertEquals(2, hashtagEdge.getNumberOfTweetsInvolved());
		assertEquals(1, node1.getNumberOfTweetsInvolved());
		assertEquals(0, node2.getNumberOfTweetsInvolved());
		
		node2.incrementNumberOfTweetsInvolved();
		assertEquals(2, hashtagEdge.getNumberOfTweetsInvolved());
		assertEquals(1, node1.getNumberOfTweetsInvolved());
		assertEquals(1, node2.getNumberOfTweetsInvolved());
	}
	
	@Test
	public void testClone() {
		HashtagEdge hashtagEdge, clonedHashtagEdge;
		HashtagNode hashtagNodeSource, hashtagNodeTarget;
		
		hashtagNodeSource = new HashtagNode(1, "NoDe1");
		hashtagNodeTarget = new HashtagNode(2, "NoDe2");
		
		hashtagEdge = new HashtagEdge(hashtagNodeSource, hashtagNodeTarget);
		hashtagEdge.incrementNumberOfTweetsInvolved();
		
		clonedHashtagEdge = hashtagEdge.clone();
		
		assertEquals(hashtagEdge.getNumberOfTweetsInvolved(), clonedHashtagEdge.getNumberOfTweetsInvolved());
		assertEquals(hashtagEdge.getSource(), clonedHashtagEdge.getSource());
		assertEquals(hashtagEdge.getTarget(), clonedHashtagEdge.getTarget());
		
		hashtagEdge.incrementNumberOfTweetsInvolved();
		hashtagEdge.getSource().setName("newNode1");
		hashtagEdge.getTarget().setName("newNode2");
		assertNotEquals(hashtagEdge.getNumberOfTweetsInvolved(), clonedHashtagEdge.getNumberOfTweetsInvolved());
		assertEquals(hashtagEdge.getSource().getNameWithCase(), clonedHashtagEdge.getSource().getNameWithCase());
		assertEquals(hashtagEdge.getTarget().getNameWithCase(), clonedHashtagEdge.getTarget().getNameWithCase());
	}

}
