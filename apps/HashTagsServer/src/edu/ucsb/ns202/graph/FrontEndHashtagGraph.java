package edu.ucsb.ns202.graph;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FrontEndHashtagGraph {
	private HashMap<String, ArrayList<HashtagEdge>> graph = new HashMap<String, ArrayList<HashtagEdge>>();
	private HashMap<String, HashtagNode> hashtagNodeMetaDataHashMap = new HashMap<String, HashtagNode>();
	private ArrayList<HashtagNode> hashtagIDarrayList = new ArrayList<HashtagNode>();
	
//	protected int totalNumberOfTweets = 0;
	protected int totalOriginalNumberOfTweets = 0;
	
	protected int totalNodesNumber = 0;
	protected int totalOriginalNodesNumber = 0;
	protected int totalNodeWeight = 0;
	protected int totalOriginalNodeWeight = 0;
	
	protected int totalEdgesNumber = 0;
	protected int totalOriginalEdgesNumber = 0;
	protected int totalEdgeWeight = 0;
	protected int totalOriginalEdgeWeight = 0;
	
	public HashtagNode addNodeByCloning(HashtagNode hashtagNode) {
		HashtagNode newHashtagNode;
		String hashtagKey;
		
		if (this.getNode(hashtagNode.getNameWithoutCase()) != null) {
			return this.getNode(hashtagNode.getNameWithoutCase());
		}
		
		newHashtagNode = hashtagNode.clone(); 		
		hashtagKey = newHashtagNode.getNameWithoutCase();
		newHashtagNode.setNodeID(this.totalNodesNumber);
		
		this.graph.put(hashtagKey, new ArrayList<HashtagEdge>());
		this.hashtagNodeMetaDataHashMap.put(hashtagKey, newHashtagNode);
		this.hashtagIDarrayList.add(newHashtagNode);
		
		this.totalNodesNumber++;
		this.totalNodeWeight += newHashtagNode.getNumberOfTweetsInvolved();
		
		return newHashtagNode;
	}
	
	public void addEdgeByCloning(HashtagEdge hashtagEdge) {
		String hashtagSourceKey, hashtagTargetKey;
		HashtagEdge clonedHashtagEdge1, clonedHashtagEdge2;
		HashtagNode hashtagNodeSource, hashtagNodeTarget;
		
		if (this.getEdge(hashtagEdge.getSource().getNameWithoutCase(), hashtagEdge.getTarget().getNameWithoutCase()) != null) {
			return;
		}
		
		hashtagSourceKey = hashtagEdge.getSource().getNameWithoutCase();
		hashtagTargetKey = hashtagEdge.getTarget().getNameWithoutCase();
		
		hashtagNodeSource = this.getNode(hashtagSourceKey);
		hashtagNodeTarget = this.getNode(hashtagTargetKey);
		
		if (hashtagNodeSource == null) {
			hashtagNodeSource = this.addNodeByCloning(hashtagEdge.getSource());
		}
		if (hashtagNodeTarget == null) {
			hashtagNodeTarget = this.addNodeByCloning(hashtagEdge.getTarget());
		}
		
		clonedHashtagEdge1 = hashtagEdge.clone();
		clonedHashtagEdge1.setSource(hashtagNodeSource);
		clonedHashtagEdge1.setTarget(hashtagNodeTarget);
		this.graph.get(hashtagSourceKey).add(clonedHashtagEdge1);
		
		clonedHashtagEdge2 = hashtagEdge.clone();
		clonedHashtagEdge2.setSource(hashtagNodeTarget);
		clonedHashtagEdge2.setTarget(hashtagNodeSource);
		this.graph.get(hashtagTargetKey).add(clonedHashtagEdge2);
		
		this.totalEdgesNumber++;
		this.totalEdgeWeight += clonedHashtagEdge1.getNumberOfTweetsInvolved();
	}
	
	public HashtagNode getNode(String hashtag) {
		String hashtagKey;
		
		hashtagKey = hashtag.toLowerCase();
		return this.hashtagNodeMetaDataHashMap.get(hashtagKey);
	}
	
	public HashtagEdge getEdge(String hashtagSource, String hashtagTarget) {
		ArrayList<HashtagEdge> hashtagEdgeList = this.graph.get(hashtagSource.toLowerCase());
		
		if (hashtagEdgeList != null) {
			for (HashtagEdge hashtagEdge : hashtagEdgeList) {
				if (hashtagTarget.equalsIgnoreCase(hashtagEdge.getTarget().getNameWithoutCase())) {
					return hashtagEdge;
				}
			}
		}
		
		return null;
	}
	
	public void setOriginalTotalNumberOfTweets(int totalOriginalNumberOfTweets) {
		this.totalOriginalNumberOfTweets = totalOriginalNumberOfTweets;
	}
	
	public int getOriginalTotalNumberOfTweets() {
		return this.totalOriginalNumberOfTweets;
	}
	
	public void setOriginalTotalNodesNumber(int totalOriginalNodesNumber) {
		this.totalOriginalNodesNumber = totalOriginalNodesNumber;
	}
	
	public int getOriginalTotalNodesNumber() {
		return this.totalOriginalNodesNumber;
	}
	
	public void setOriginalTotalEdgesNumber(int totalOriginalEdgesNumber) {
		this.totalOriginalEdgesNumber = totalOriginalEdgesNumber;
	}
	
	public int getOriginalTotalEdgesNumber() {
		return this.totalOriginalEdgesNumber;
	}
	
	public void setOriginalTotalNodeWeight(int totalOriginalNodeWeight) {
		this.totalOriginalNodeWeight = totalOriginalNodeWeight;
	}
	
	public int getOriginalTotalNodeWeight() {
		return this.totalOriginalNodeWeight;
	}
	
	public void setOriginalTotalEdgeWeight(int totalOriginalEdgeWeight) {
		this.totalOriginalEdgeWeight = totalOriginalEdgeWeight;
	}
	
	public int getOriginalTotalEdgeWeight() {
		return this.totalOriginalEdgeWeight;
	}
	
	public JSONObject getNodesAndEdgesAsJSON() {
		JSONObject result = new JSONObject();
		try {
			result.put("nodes", this.getNodesAsJSON());
			result.put("links", this.getEdgesAsJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONArray getNodesAsJSON() {
		JSONArray nodesJSON = new JSONArray();
		JSONObject nodeJSON;
		HashtagNode nodeMetaData;
		String hashtagWithCase;
		
		for (HashtagNode hashtagNode : this.hashtagIDarrayList) {	
			nodeJSON = new JSONObject();
			try {
//				System.out.println("Node name:" + hashtagNode.getNameWithCase() + " | id:" + hashtagNode.getNodeID() + " | NbTweets:" + hashtagNode.getTotalTweetNumber() + " | totalOriginalTweets:" + this.totalOriginalNumberOfTweets  + " | radius:" + computeD3NodeRadius(hashtagNode));
				nodeJSON.put("name", hashtagNode.getNameWithCase());
				nodeJSON.put("id", hashtagNode.getNodeID());
				nodeJSON.put("radius", computeD3NodeRadius(hashtagNode));
				nodeJSON.put("type", hashtagNode.getType());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			nodesJSON.put(nodeJSON);
		}
		return nodesJSON;
	}
	
	public JSONArray getEdgesAsJSON() {
		JSONArray edgesJSON = new JSONArray();
		JSONObject edgeJSON;
		HashtagNode hashtagNodeSource;
		HashtagNode hashtagNodeTarget;
		ArrayList<HashtagEdge> hashtagEdgeList;
		
		for (String hashtagSource : this.graph.keySet()) {
			hashtagEdgeList = this.graph.get(hashtagSource);
			hashtagNodeSource = this.hashtagNodeMetaDataHashMap.get(hashtagSource);
			
			for (HashtagEdge hashtagEdge : hashtagEdgeList) {
				hashtagNodeTarget = hashtagEdge.getTarget();
				
				if (hashtagNodeSource.getNodeID() < hashtagNodeTarget.getNodeID()) {
					edgeJSON = new JSONObject();
					try {
						System.out.println("edge source:" + hashtagNodeSource.getNameWithCase() + " | target:" + hashtagNodeTarget.getNameWithCase() + " | nbTweets:" + hashtagEdge.getNumberOfTweetsInvolved() + " | totalOriginalTweets:" + this.totalOriginalNumberOfTweets + " | weight:" + computeD3EdgeWeight(hashtagEdge));
						edgeJSON.put("source", hashtagNodeSource.getNodeID());
						edgeJSON.put("target", hashtagNodeTarget.getNodeID());
						edgeJSON.put("weight", computeD3EdgeWeight(hashtagEdge));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					edgesJSON.put(edgeJSON);
				}
			}
		}
		return edgesJSON;
	}
	
	public double computeD3EdgeWeight(HashtagEdge hashtagEdge) {
		// minimum width-stroke for D3: 0.3 (preferred: 0.8)
		// maximum width-stroke for D3: 7
		double minD3StrokeWidth, maxD3StrokeWidth, minimumPossibleTweetFraction;
		double edgeTweetFraction, result;
		
		minD3StrokeWidth = 0.8;
		maxD3StrokeWidth = 7.0;
		minimumPossibleTweetFraction = 1.0 / this.totalOriginalNumberOfTweets;
		
		edgeTweetFraction = (double)hashtagEdge.getNumberOfTweetsInvolved()/this.totalOriginalNumberOfTweets;
				
		result = minD3StrokeWidth + (maxD3StrokeWidth - minD3StrokeWidth) * 
				(edgeTweetFraction-minimumPossibleTweetFraction)/(1-minimumPossibleTweetFraction);
		
		return new BigDecimal(result).setScale(2, RoundingMode.CEILING).doubleValue();
	}
	
	public double computeD3NodeRadius(HashtagNode hashtagNode) {
		double minD3Radius, maxD3Radius, minimumPossibleTweetFraction, nodeTweetFraction;
		double result;
		
		// minimum radius for D3: 1
		// maximum radius for D3: 15
		minD3Radius = 3.0;
		maxD3Radius = 13.0;
		minimumPossibleTweetFraction = 1.0 / this.totalOriginalNumberOfTweets;
		
		nodeTweetFraction = (double)hashtagNode.getNumberOfTweetsInvolved()/this.totalOriginalNumberOfTweets; 
		
		result = minD3Radius + (maxD3Radius - minD3Radius) * 
				(nodeTweetFraction-minimumPossibleTweetFraction)/(1-minimumPossibleTweetFraction);
		
		return new BigDecimal(result).setScale(2, RoundingMode.CEILING).doubleValue();
	}
}
