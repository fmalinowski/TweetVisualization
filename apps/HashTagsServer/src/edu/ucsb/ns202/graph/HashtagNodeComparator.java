package edu.ucsb.ns202.graph;

import java.util.Comparator;

public class HashtagNodeComparator implements Comparator<HashtagNode> {

	@Override
	public int compare(HashtagNode hashtagNode1, HashtagNode hashtagNode2) {
		return hashtagNode2.getTotalTweetNumber() - hashtagNode1.getTotalTweetNumber();
	}

}

