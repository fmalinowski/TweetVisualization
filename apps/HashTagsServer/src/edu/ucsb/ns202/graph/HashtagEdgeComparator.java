package edu.ucsb.ns202.graph;

import java.util.Comparator;

public class HashtagEdgeComparator implements Comparator<HashtagEdge> {

	@Override
	public int compare(HashtagEdge hashtagEdge1, HashtagEdge hashtagEdge2) {
		return hashtagEdge2.getNumberOfTweetsInvolved() - hashtagEdge1.getNumberOfTweetsInvolved();
	}

}