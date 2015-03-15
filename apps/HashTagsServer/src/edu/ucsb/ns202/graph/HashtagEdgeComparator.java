package edu.ucsb.ns202.graph;

import java.util.Comparator;

public class HashtagEdgeComparator implements Comparator<HashtagEdge> {

	private String hashtagSource;
	
	public HashtagEdgeComparator(String hashtagSource) {
		this.hashtagSource = hashtagSource;
	}

	@Override
	public int compare(HashtagEdge hashtagEdge1, HashtagEdge hashtagEdge2) {
		HashtagNode edge1TargetNode, edge2TargetNode;
		
		if (hashtagEdge1.getSource().getNameWithoutCase().equalsIgnoreCase(this.hashtagSource)) {
			edge1TargetNode = hashtagEdge1.getTarget();
		}
		else {
			edge1TargetNode = hashtagEdge1.getSource();
		}
		
		if (hashtagEdge2.getSource().getNameWithoutCase().equalsIgnoreCase(this.hashtagSource)) {
			edge2TargetNode = hashtagEdge2.getTarget();
		}
		else {
			edge2TargetNode = hashtagEdge2.getSource();
		}
		
		return edge2TargetNode.getNumberOfTweetsInvolved() - edge1TargetNode.getNumberOfTweetsInvolved();
	}

}
