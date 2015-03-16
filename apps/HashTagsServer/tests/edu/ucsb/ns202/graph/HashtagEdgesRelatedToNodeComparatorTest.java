package edu.ucsb.ns202.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class HashtagEdgesRelatedToNodeComparatorTest {

	@Test
	public void testCompare() {
		ArrayList<HashtagEdge> arrayList = new ArrayList<HashtagEdge>();
		
		HashtagNode hashtagNode1, hashtagNode2, hashtagNode3, hashtagNode4;
		HashtagEdge hashtagEdge1, hashtagEdge2, hashtagEdge3;
		
		hashtagNode1 = new HashtagNode(1, "node1");
		hashtagNode2 = new HashtagNode(2, "node2");
		hashtagNode3 = new HashtagNode(3, "node3");
		hashtagNode4 = new HashtagNode(4, "node4");
		
		hashtagNode1.incrementNumberOfTweetsInvolved();
		hashtagNode1.incrementNumberOfTweetsInvolved();
		hashtagNode1.incrementNumberOfTweetsInvolved();
		
		hashtagNode2.incrementNumberOfTweetsInvolved();
		
		hashtagNode3.incrementNumberOfTweetsInvolved();
		hashtagNode3.incrementNumberOfTweetsInvolved();
		hashtagNode3.incrementNumberOfTweetsInvolved();
		hashtagNode3.incrementNumberOfTweetsInvolved();
		
		hashtagNode4.incrementNumberOfTweetsInvolved();
		hashtagNode4.incrementNumberOfTweetsInvolved();
		hashtagNode4.incrementNumberOfTweetsInvolved();
		
		hashtagEdge1 = new HashtagEdge(hashtagNode1, hashtagNode2);
		hashtagEdge2 = new HashtagEdge(hashtagNode3, hashtagNode1);
		hashtagEdge3 = new HashtagEdge(hashtagNode1, hashtagNode4);
		
		arrayList.add(hashtagEdge1);
		arrayList.add(hashtagEdge2);
		arrayList.add(hashtagEdge3);
		
		HashtagEdgesRelatedToNodeComparator hashtagEdgesRelatedComparator = new HashtagEdgesRelatedToNodeComparator("node1");
		
		Collections.sort(arrayList, hashtagEdgesRelatedComparator);
		
		assertEquals(hashtagEdge2, arrayList.get(0));
		assertEquals(hashtagEdge3, arrayList.get(1));
		assertEquals(hashtagEdge1, arrayList.get(2));
	}

}
