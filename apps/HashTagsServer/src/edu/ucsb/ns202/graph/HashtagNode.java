package edu.ucsb.ns202.graph;

public class HashtagNode {
	
	private int nodeID;
	private String nameWithCase;
	private int totalTweetNumberWithOneHashtag = 0;
	private int totalTweetNumber = 0;
	private enum Type { HASHTAG, MENTION }
	private Type hashtagOrMention = Type.HASHTAG;
	
	public HashtagNode(int nodeID, String nameWithCase) {
		this.nodeID = nodeID;
		this.nameWithCase = nameWithCase;
	}
	
	public int getNodeID() {
		return this.nodeID;
	}
	
	public String getNameWithCase() {
		return this.nameWithCase;
	}
	
	public String getNameWithoutCase() {
		return this.nameWithCase.toLowerCase();
	}
	
	public void setTypeMention() {
		this.hashtagOrMention = Type.MENTION;
	}
	
	public String getType() {
		String type;
		switch(hashtagOrMention) {
		case HASHTAG:
			type = "#";
			break;
		case MENTION:
			type = "@";
			break;
		default: 
			type = "err";
			break;
		}
		return type;
	}
	
	protected void incrementTotalTweetNumberWithOneHashtag() {
		this.totalTweetNumberWithOneHashtag++;
		this.totalTweetNumber++;
	}
	
	public int getTotalTweetNumberWithOneHashtag() {
		return this.totalTweetNumberWithOneHashtag;
	}
	
	protected void incrementTotalTweetNumber() {
		this.totalTweetNumber++;
	}
	
	public int getTotalTweetNumber() {
		return this.totalTweetNumber;
	}
}
