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
	public void testIncrementTotalTweetNumberWithOneHashtag() {
		HashtagNode hashtagNode = new HashtagNode(1, "FlippingTest");
		
		assertEquals(0, hashtagNode.getTotalTweetNumberWithOneHashtag());
		hashtagNode.incrementTotalTweetNumberWithOneHashtag();
		assertEquals(1, hashtagNode.getTotalTweetNumberWithOneHashtag());
		hashtagNode.incrementTotalTweetNumber();
		assertEquals(1, hashtagNode.getTotalTweetNumberWithOneHashtag());
		hashtagNode.incrementTotalTweetNumberWithOneHashtag();
		assertEquals(2, hashtagNode.getTotalTweetNumberWithOneHashtag());
	}

	@Test
	public void testIncrementTotalTweetNumber() {
		HashtagNode hashtagNode = new HashtagNode(1, "FlippingTest");
		
		assertEquals(0, hashtagNode.getTotalTweetNumber());
		hashtagNode.incrementTotalTweetNumber();
		assertEquals(1, hashtagNode.getTotalTweetNumber());
		hashtagNode.incrementTotalTweetNumberWithOneHashtag();
		assertEquals(2, hashtagNode.getTotalTweetNumber());
		hashtagNode.incrementTotalTweetNumber();
		assertEquals(3, hashtagNode.getTotalTweetNumber());
	}
	
	@Test
	public void testClone() {
		HashtagNode hashtagNode, clonedHashtagNode;
		
		hashtagNode = new HashtagNode(2, "myNode");
		hashtagNode.setTypeMention();
		hashtagNode.incrementTotalTweetNumber();
		hashtagNode.incrementTotalTweetNumberWithOneHashtag();
		hashtagNode.incrementTotalTweetNumberWithOneHashtag();
		
		clonedHashtagNode = hashtagNode.clone();
		assertEquals(hashtagNode.getNodeID(), clonedHashtagNode.getNodeID());
		assertEquals(hashtagNode.getNameWithCase(), clonedHashtagNode.getNameWithCase());
		assertEquals(hashtagNode.getTotalTweetNumber(), clonedHashtagNode.getTotalTweetNumber());
		assertEquals(hashtagNode.getTotalTweetNumberWithOneHashtag(), clonedHashtagNode.getTotalTweetNumberWithOneHashtag());
		assertEquals(hashtagNode.getType(), clonedHashtagNode.getType());
		
		hashtagNode.setNodeID(3);
		hashtagNode.setName("NewNodeName");
		hashtagNode.setTypeHashtag();
		hashtagNode.incrementTotalTweetNumber();
		hashtagNode.incrementTotalTweetNumberWithOneHashtag();
		assertNotEquals(hashtagNode.getNodeID(), clonedHashtagNode.getNodeID());
		assertNotEquals(hashtagNode.getNameWithCase(), clonedHashtagNode.getNameWithCase());
		assertNotEquals(hashtagNode.getTotalTweetNumber(), clonedHashtagNode.getTotalTweetNumber());
		assertNotEquals(hashtagNode.getTotalTweetNumberWithOneHashtag(), clonedHashtagNode.getTotalTweetNumberWithOneHashtag());
		assertNotEquals(hashtagNode.getType(), clonedHashtagNode.getType());
	}

}
