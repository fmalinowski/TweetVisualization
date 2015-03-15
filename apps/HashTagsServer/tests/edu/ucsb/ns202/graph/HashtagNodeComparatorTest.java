package edu.ucsb.ns202.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class HashtagNodeComparatorTest {

	@Test
	public void testCompare() {
		ArrayList<HashtagNode> arrayList = new ArrayList<HashtagNode>();
		
		HashtagNode hashtagNode1, hashtagNode2, hashtagNode3, hashtagNode4;
		
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
		
		arrayList.add(hashtagNode1);
		arrayList.add(hashtagNode2);
		arrayList.add(hashtagNode3);
		arrayList.add(hashtagNode4);
		
		HashtagNodeComparator hashtagNodeComparator = new HashtagNodeComparator();
		
		Collections.sort(arrayList, hashtagNodeComparator);
		
		assertEquals(hashtagNode3, arrayList.get(0));
		assertEquals(hashtagNode1, arrayList.get(1));
		assertEquals(hashtagNode4, arrayList.get(2));
		assertEquals(hashtagNode2, arrayList.get(3));
	}

}
