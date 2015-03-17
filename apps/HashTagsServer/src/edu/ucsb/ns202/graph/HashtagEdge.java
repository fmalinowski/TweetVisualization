package edu.ucsb.ns202.graph;

public class HashtagEdge {
	
	private HashtagNode source;
	private HashtagNode target;
	private int edgeRank;
	
	private int totalTweets = 0;
	
	public HashtagEdge(HashtagNode source, HashtagNode target) {
		this.source = source;
		this.target = target;
	}
	
	// This doesn't clone the source and the target!
	public HashtagEdge clone() {
		HashtagEdge clonedHashtagEdge = new HashtagEdge(this.source, this.target);
		clonedHashtagEdge.totalTweets = this.totalTweets;
		
		return clonedHashtagEdge;
	}
	
	public HashtagNode getSource() {
		return this.source;
	}
	
	public HashtagNode getTarget() {
		return this.target;
	}
	
	public void setSource(HashtagNode hashtagNodeSource) {
		this.source = hashtagNodeSource;
	}
	
	public void setTarget(HashtagNode hashtagNodeTarget) {
		this.target = hashtagNodeTarget;
	}
	
	public int getNumberOfTweetsInvolved() {
		return this.totalTweets;
	}
	
	protected void incrementNumberOfTweetsInvolved() {
		this.totalTweets++;
	}
	
	public void setEdgeRank(int edgeRank) {
		this.edgeRank = edgeRank;
	}
	
	public int getEdgeRank() {
		return this.edgeRank;
	}
}
