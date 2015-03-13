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

}
