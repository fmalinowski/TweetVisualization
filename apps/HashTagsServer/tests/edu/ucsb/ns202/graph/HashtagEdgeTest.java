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
		assertEquals(0, hashtagEdge.getNumberOfTweets());
		assertEquals(0, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(0, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		
		hashtagEdge.incrementNumberOfTweets();
		assertEquals(1, hashtagEdge.getNumberOfTweets());
		assertEquals(1, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		
		node1.incrementTotalTweetNumber();
		assertEquals(1, hashtagEdge.getNumberOfTweets());
		assertEquals(2, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		
		hashtagEdge.incrementNumberOfTweets();
		assertEquals(2, hashtagEdge.getNumberOfTweets());
		assertEquals(3, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(2, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		
		node2.incrementTotalTweetNumberWithOneHashtag();
		assertEquals(2, hashtagEdge.getNumberOfTweets());
		assertEquals(3, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(3, node2.getTotalTweetNumber());
		assertEquals(1, node2.getTotalTweetNumberWithOneHashtag());
	}
	
	@Test
	public void testIncrementNumberOfTweetsWithoutIncrementingTweetNbAtNodes() {
		HashtagNode node1 = new HashtagNode(1, "yo");
		HashtagNode node2 = new HashtagNode(2, "wassup");
		
		HashtagEdge hashtagEdge = new HashtagEdge(node1, node2);
		assertEquals(0, hashtagEdge.getNumberOfTweets());
		assertEquals(0, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(0, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		
		hashtagEdge.incrementNumberOfTweetsWithoutIncrementingTweetNbAtNodes();
		assertEquals(1, hashtagEdge.getNumberOfTweets());
		assertEquals(0, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(0, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		
		hashtagEdge.incrementNumberOfTweets();
		assertEquals(2, hashtagEdge.getNumberOfTweets());
		assertEquals(1, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
		
		hashtagEdge.incrementNumberOfTweetsWithoutIncrementingTweetNbAtNodes();
		assertEquals(3, hashtagEdge.getNumberOfTweets());
		assertEquals(1, node1.getTotalTweetNumber());
		assertEquals(0, node1.getTotalTweetNumberWithOneHashtag());
		assertEquals(1, node2.getTotalTweetNumber());
		assertEquals(0, node2.getTotalTweetNumberWithOneHashtag());
	}

}
