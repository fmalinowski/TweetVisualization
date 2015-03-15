package edu.ucsb.ns202.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class HashtagEdgeComparatorTest {

	@Test
	public void testCompare() {
		ArrayList<HashtagEdge> arrayList = new ArrayList<HashtagEdge>();
		
		HashtagNode hashtagNode1, hashtagNode2, hashtagNode3, hashtagNode4;
		HashtagEdge hashtagEdge1, hashtagEdge2, hashtagEdge3;
		
		hashtagNode1 = new HashtagNode(1, "node1");
		hashtagNode2 = new HashtagNode(2, "node2");
		hashtagNode3 = new HashtagNode(3, "node3");
		hashtagNode4 = new HashtagNode(4, "node4");
		
		hashtagNode1.incrementTotalTweetNumber();
		hashtagNode1.incrementTotalTweetNumber();
		hashtagNode1.incrementTotalTweetNumber();
		
		hashtagNode2.incrementTotalTweetNumber();
		
		hashtagNode3.incrementTotalTweetNumber();
		hashtagNode3.incrementTotalTweetNumber();
		hashtagNode3.incrementTotalTweetNumber();
		hashtagNode3.incrementTotalTweetNumber();
		
		hashtagNode4.incrementTotalTweetNumber();
		hashtagNode4.incrementTotalTweetNumber();
		hashtagNode4.incrementTotalTweetNumber();
		
		hashtagEdge1 = new HashtagEdge(hashtagNode1, hashtagNode2);
		hashtagEdge2 = new HashtagEdge(hashtagNode3, hashtagNode1);
		hashtagEdge3 = new HashtagEdge(hashtagNode1, hashtagNode4);
		
		arrayList.add(hashtagEdge1);
		arrayList.add(hashtagEdge2);
		arrayList.add(hashtagEdge3);
		
		HashtagEdgeComparator hashtagEdgeComparator = new HashtagEdgeComparator("node1");
		
		Collections.sort(arrayList, hashtagEdgeComparator);
		
		assertEquals(hashtagEdge2, arrayList.get(0));
		assertEquals(hashtagEdge3, arrayList.get(1));
		assertEquals(hashtagEdge1, arrayList.get(2));
	}

}
