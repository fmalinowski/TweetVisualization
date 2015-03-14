package edu.ucsb.ns202.offline;

import java.util.HashMap;

import edu.ucsb.ns202.graph.HashtagGraph;
import edu.ucsb.ns202.graph.HashtagNode;

public class OfflineGraphBuilder {
	
	private HashMap<String, HashtagNode> visitedNodes = new HashMap<String, HashtagNode>();
	private HashtagGraph hashtagGraph;
	private int maxHops;
	private int childrenNodeNb;
	
	public OfflineGraphBuilder(HashtagGraph hashtagGraph, int maxHops, int childrenNodeNb) {
		this.hashtagGraph = hashtagGraph;
		this.maxHops = maxHops;
	}
	
//	public void findPopularNodesForHashtag 

}
