package edu.ucsb.ns202.offline;

import java.util.ArrayList;

import edu.ucsb.ns202.graph.FrontEndHashtagGraph;
import edu.ucsb.ns202.graph.HashtagEdge;
import edu.ucsb.ns202.graph.HashtagNode;
import edu.ucsb.ns202.graph.SortedHashtagGraph;

public class FrontEndGraphBuilder {
	
	private SortedHashtagGraph hashtagGraph;
	private String hashtag;
	private int[] nodesPerLevel;

	public FrontEndGraphBuilder(SortedHashtagGraph hashtagGraph, String hashtag, 
			int[] nodesPerLevel) {
		this.hashtagGraph = hashtagGraph;
		this.hashtag = hashtag.toLowerCase();
		this.nodesPerLevel = nodesPerLevel;
	}
	
	public FrontEndHashtagGraph buildGraph() {
		FrontEndHashtagGraph frontEndHashtagGraph; 
		ArrayList<String> nodesToBeProcessed;
		ArrayList<Integer> levelsBeingProcessed;
		ArrayList<String> nodesProcessed;
		ArrayList<HashtagEdge> edgeListFromOriginalGraph;
		HashtagNode currentNode;
		HashtagEdge originalEdge;
		String currentNodeString;
		String originalEdgeTargetString;
		int currentLevel, nextNodeLevel;
		
		frontEndHashtagGraph = new FrontEndHashtagGraph();
		currentLevel = 0;
		nextNodeLevel = 1;
		
		nodesToBeProcessed = new ArrayList<String>();
		levelsBeingProcessed = new ArrayList<Integer>();
		nodesProcessed = new ArrayList<String>();
		
		nodesToBeProcessed.add(this.hashtag);
		levelsBeingProcessed.add(currentLevel);
		
		while ((nextNodeLevel < this.nodesPerLevel.length) && !nodesToBeProcessed.isEmpty()) {
			currentNodeString = nodesToBeProcessed.remove(0);
			currentLevel = levelsBeingProcessed.remove(0);
			
			currentNode = this.hashtagGraph.getHashtagNode(currentNodeString);
			edgeListFromOriginalGraph = this.hashtagGraph.getEdges(currentNodeString);
			
			frontEndHashtagGraph.addNodeByCloning(currentNode);
			
			int addedNode = 0;
			int currentEdgeIndex = 0;
			
			while (addedNode < this.nodesPerLevel[currentLevel]) {
				if (edgeListFromOriginalGraph.size() <= currentEdgeIndex) {
					break;
				}
				originalEdge = edgeListFromOriginalGraph.get(currentEdgeIndex);
				frontEndHashtagGraph.addEdgeByCloning(originalEdge);
				
				originalEdgeTargetString = originalEdge.getTarget().getNameWithoutCase();
				if (!nodesToBeProcessed.contains(originalEdgeTargetString) &&
						!nodesProcessed.contains(originalEdgeTargetString)) {
					nodesToBeProcessed.add(originalEdgeTargetString);
					levelsBeingProcessed.add(currentLevel + 1);
					addedNode++;
				}
				
				currentEdgeIndex++;
			}
			
			if (!nodesToBeProcessed.isEmpty()) {
				nextNodeLevel = levelsBeingProcessed.get(0);
			}
		}
		
		return frontEndHashtagGraph;
	}

}
