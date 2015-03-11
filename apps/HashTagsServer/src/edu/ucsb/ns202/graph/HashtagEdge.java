package edu.ucsb.ns202.graph;

public class HashtagEdge {
	
	private HashtagNode source;
	private HashtagNode target;
	
	private int totalTweets = 0;
	
	public HashtagEdge(HashtagNode source, HashtagNode target) {
		this.source = source;
		this.target = target;
	}
	
	public HashtagNode getSource() {
		return this.source;
	}
	
	public HashtagNode getTarget() {
		return this.target;
	}
	
	public int getNumberOfTweets() {
		return this.totalTweets;
	}
	
	protected void incrementNumberOfTweets() {
		this.source.incrementTotalTweetNumber();
		this.target.incrementTotalTweetNumber();
		this.totalTweets++;
	}
}
