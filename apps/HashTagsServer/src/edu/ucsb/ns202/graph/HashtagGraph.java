package edu.ucsb.ns202.graph;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HashtagGraph {
	
	private HashMap<String, ArrayList<HashtagEdge>> graph = new HashMap<String, ArrayList<HashtagEdge>>();
	private HashMap<String, HashtagNode> hashtagNodeMetaDataHashMap = new HashMap<String, HashtagNode>();
	private ArrayList<HashtagNode> hashtagIDarrayList = new ArrayList<HashtagNode>();
	
	private int totalNumberOfTweets = 0;
	private int totalNumberOfTweetsWithOneHashtag = 0;
	
	private int totalNodesNumber = 0;

	public void addNode(String hashtag) {
		HashtagNode hashtagNode = this.addNodeWithoutIncrementingWeight(hashtag);
		this.incrementNodeWeight(hashtag);
	}	
	
	public void incrementNodeWeight(String hashtag) {
		HashtagNode hashtagNode = this.getHashtagNode(hashtag);
		
		if (hashtagNode != null) {
			hashtagNode.incrementTotalTweetNumberWithOneHashtag();
			this.totalNumberOfTweets++;
			this.totalNumberOfTweetsWithOneHashtag++;
		}
	}
	
	public void addEdge(String hashtagSource, String hashtagTarget) {
		HashtagNode hashtagNodeSource;
		HashtagNode hashtagNodeTarget;
		HashtagEdge hashtagEdge1, hashtagEdge2;
		String hashTagSourceKey = hashtagSource.toLowerCase();
		String hashTagTargetKey = hashtagTarget.toLowerCase();
		
		if (hashTagSourceKey.equals(hashTagTargetKey)) {
			return;
		}
		
		if (!this.hasNode(hashtagSource)) {
			this.addNodeWithoutIncrementingWeight(hashtagSource); //Add function addNodeWithoutIncrementingWeight();
		}
		if (!this.hasNode(hashtagTarget)) {
			this.addNodeWithoutIncrementingWeight(hashtagTarget); //Add function addNodeWithoutIncrementingWeight();
		}
		
		hashtagNodeSource = this.hashtagNodeMetaDataHashMap.get(hashTagSourceKey);
		hashtagNodeTarget = this.hashtagNodeMetaDataHashMap.get(hashTagTargetKey);
		
		if (!this.hasEdge(hashTagSourceKey, hashTagTargetKey)) {
			hashtagEdge1 = new HashtagEdge(hashtagNodeSource, hashtagNodeTarget);
			this.graph.get(hashTagSourceKey).add(hashtagEdge1);
		}
		else {
			hashtagEdge1 = this.getHashtagEdge(hashTagSourceKey, hashTagTargetKey);
		}
		if (!this.hasEdge(hashTagTargetKey, hashTagSourceKey)) {
			hashtagEdge2 = new HashtagEdge(hashtagNodeTarget, hashtagNodeSource);
			this.graph.get(hashTagTargetKey).add(hashtagEdge2);
		}
		else {
			hashtagEdge2 = this.getHashtagEdge(hashTagTargetKey, hashTagSourceKey);
		}
		
		hashtagEdge1.incrementNumberOfTweets();
		hashtagEdge2.incrementNumberOfTweetsWithoutIncrementingTweetNbAtNodes();
		this.totalNumberOfTweets++;
	}
	
	public void incrementEdgeWeight(String hashtagSource, String hashtagTarget) {
		HashtagEdge hashtagEdge1 = this.getHashtagEdge(hashtagSource, hashtagTarget);
		HashtagEdge hashtagEdge2 = this.getHashtagEdge(hashtagTarget, hashtagSource);
		
		if (hashtagEdge1 != null && hashtagEdge2 != null) {
			hashtagEdge1.incrementNumberOfTweets();
			hashtagEdge2.incrementNumberOfTweetsWithoutIncrementingTweetNbAtNodes();
			this.totalNumberOfTweets++;
		}
	}
	
	public Boolean hasNode(String hashtag) {
		return this.graph.containsKey(hashtag.toLowerCase());
	}
	
	public Boolean hasEdge(String hashtagSource, String hashtagTarget) {
		String hashTagSourceKey = hashtagSource.toLowerCase();
		String hashTagTargetKey = hashtagTarget.toLowerCase();
				
		ArrayList<HashtagEdge> hashtagEdgeList = this.graph.get(hashTagSourceKey);
		
		if(hashtagEdgeList != null) {
			for (HashtagEdge hashtagEdge : hashtagEdgeList) {
				if (hashtagEdge.getTarget().getNameWithoutCase().equalsIgnoreCase(hashTagTargetKey)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public HashtagNode getHashtagNode(String hashtag) {
		return this.hashtagNodeMetaDataHashMap.get(hashtag.toLowerCase());
	}
	
	public HashtagEdge getHashtagEdge(String hashtagSource, String hashtagTarget) {
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
	
	public int getCountOfEdgesFromSource(String hashtag) {
		String hashTagSourceKey = hashtag.toLowerCase();
		ArrayList<HashtagEdge> hashtagEdgeList = this.graph.get(hashTagSourceKey);
		
		if (hashtagEdgeList == null) {
			return 0;
		}
		return hashtagEdgeList.size();
	}
	
	public int getCountOfNodes() {
		return this.graph.size();
	}

	public ArrayList<HashtagNode> getNodes() {
		ArrayList<HashtagNode> hashtagNodeList = new ArrayList<HashtagNode>();
		for (HashtagNode hashtagNode : hashtagIDarrayList) {
			hashtagNodeList.add(hashtagNode);
		}
		
		return hashtagNodeList;
	}
	
	public ArrayList<String> getNodeStringList() {
		ArrayList<String> hashtagNodeStringList = new ArrayList<String>();
		for (HashtagNode hashtagNode : hashtagIDarrayList) {
			hashtagNodeStringList.add(hashtagNode.getNameWithCase());
		}
		
		return hashtagNodeStringList;
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
				System.out.println("Node name:" + hashtagNode.getNameWithCase() + " | id:" + hashtagNode.getNodeID() + " | NbTweets:" + hashtagNode.getTotalTweetNumber() + " | totalTweets:" + this.totalNumberOfTweets  + " | radius:" + computeD3NodeRadius(hashtagNode));
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
						System.out.println("edge source:" + hashtagNodeSource.getNameWithCase() + " | target:" + hashtagNodeTarget.getNameWithCase() + " | nbTweets:" + hashtagEdge.getNumberOfTweets() + " | totalTweets:" + this.totalNumberOfTweets + " | weight:" + computeD3EdgeWeight(hashtagEdge));
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
	
	public int getTotalNumberOfTweets() {
		return this.totalNumberOfTweets;
	}
	
	public double computeD3EdgeWeight(HashtagEdge hashtagEdge) {
		// minimum width-stroke for D3: 0.3 (preferred: 0.8)
		// maximum width-stroke for D3: 7
		double minD3StrokeWidth, maxD3StrokeWidth, minimumPossibleTweetFraction;
		double edgeTweetFraction, result;
		
		minD3StrokeWidth = 0.8;
		maxD3StrokeWidth = 7.0;
		minimumPossibleTweetFraction = 1.0 / this.totalNumberOfTweets;
		
		edgeTweetFraction = (double)hashtagEdge.getNumberOfTweets()/this.totalNumberOfTweets;
				
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
		minimumPossibleTweetFraction = 1.0 / this.totalNumberOfTweets;
		
		nodeTweetFraction = (double)hashtagNode.getTotalTweetNumber()/this.totalNumberOfTweets; 
		
		result = minD3Radius + (maxD3Radius - minD3Radius) * 
				(nodeTweetFraction-minimumPossibleTweetFraction)/(1-minimumPossibleTweetFraction);
		
		return new BigDecimal(result).setScale(2, RoundingMode.CEILING).doubleValue();
	}
	
	private HashtagNode addNodeWithoutIncrementingWeight(String hashtag) {
		String hashTagKey = hashtag.toLowerCase();
		HashtagNode hashtagNode = this.hashtagNodeMetaDataHashMap.get(hashTagKey);
		
		if (hashtagNode == null) {
			hashtagNode = new HashtagNode(totalNodesNumber, hashtag);
			if(hashTagKey.startsWith("@")) {
				hashtagNode.setTypeMention();
			}
			
			this.graph.put(hashTagKey, new ArrayList<HashtagEdge>());
			this.hashtagNodeMetaDataHashMap.put(hashTagKey, hashtagNode);
			this.hashtagIDarrayList.add(hashtagNode);
			
			this.totalNodesNumber++;
		}
		
		return hashtagNode;
	}
}
