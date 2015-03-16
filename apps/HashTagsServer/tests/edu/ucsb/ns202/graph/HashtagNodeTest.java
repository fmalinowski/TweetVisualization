package edu.ucsb.ns202.graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class HashtagNodeTest {

	@Test
	public void testGetNameWithCaseAndWithoutCase() {
		HashtagNode hashtagNode = new HashtagNode(1, "FlippingTest");
		assertEquals("FlippingTest", hashtagNode.getNameWithCase());
		assertEquals("flippingtest", hashtagNode.getNameWithoutCase());
	}

	@Test
	public void testIncrementTotalTweetNumber() {
		HashtagNode hashtagNode = new HashtagNode(1, "FlippingTest");
		
		assertEquals(0, hashtagNode.getNumberOfTweetsInvolved());
		hashtagNode.incrementNumberOfTweetsInvolved();
		assertEquals(1, hashtagNode.getNumberOfTweetsInvolved());
		hashtagNode.incrementNumberOfTweetsInvolved();
		assertEquals(2, hashtagNode.getNumberOfTweetsInvolved());
		hashtagNode.incrementNumberOfTweetsInvolved();
		assertEquals(3, hashtagNode.getNumberOfTweetsInvolved());
	}
	
	@Test
	public void testClone() {
		HashtagNode hashtagNode, clonedHashtagNode;
		
		hashtagNode = new HashtagNode(2, "myNode");
		hashtagNode.setTypeMention();
		hashtagNode.incrementNumberOfTweetsInvolved();
		
		clonedHashtagNode = hashtagNode.clone();
		assertEquals(hashtagNode.getNodeID(), clonedHashtagNode.getNodeID());
		assertEquals(hashtagNode.getNameWithCase(), clonedHashtagNode.getNameWithCase());
		assertEquals(hashtagNode.getNumberOfTweetsInvolved(), clonedHashtagNode.getNumberOfTweetsInvolved());
		assertEquals(hashtagNode.getType(), clonedHashtagNode.getType());
		
		hashtagNode.setNodeID(3);
		hashtagNode.setName("NewNodeName");
		hashtagNode.setTypeHashtag();
		hashtagNode.incrementNumberOfTweetsInvolved();
		assertNotEquals(hashtagNode.getNodeID(), clonedHashtagNode.getNodeID());
		assertNotEquals(hashtagNode.getNameWithCase(), clonedHashtagNode.getNameWithCase());
		assertNotEquals(hashtagNode.getNumberOfTweetsInvolved(), clonedHashtagNode.getNumberOfTweetsInvolved());
		assertNotEquals(hashtagNode.getType(), clonedHashtagNode.getType());
	}

}
