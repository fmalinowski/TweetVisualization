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
	
	public HashtagNode clone() {
		HashtagNode clonedHashtagNode = new HashtagNode(this.nodeID, nameWithCase);
		clonedHashtagNode.totalTweetNumberWithOneHashtag = this.totalTweetNumberWithOneHashtag;
		clonedHashtagNode.totalTweetNumber = this.totalTweetNumber;
		clonedHashtagNode.hashtagOrMention = this.hashtagOrMention;
		
		return clonedHashtagNode;
	}
	
	public int getNodeID() {
		return this.nodeID;
	}
	
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	
	public void setName(String nameWithCase) {
		this.nameWithCase = nameWithCase;
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
	
	public void setTypeHashtag() {
		this.hashtagOrMention = Type.HASHTAG;
	}
	
	public int getType() {
		int type;
		switch(hashtagOrMention) {
		case HASHTAG:
			type = 0;
			break;
		case MENTION:
			type = 1;
			break;
		default: 
			type = -1;
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
