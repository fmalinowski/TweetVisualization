package edu.ucsb.ns202.graph;

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
		String hashTagKey = hashtag.toLowerCase();
		if (!this.graph.containsKey(hashTagKey)) {
			HashtagNode hashtagNode = new HashtagNode(totalNodesNumber, hashtag);
			
			this.graph.put(hashTagKey, new ArrayList<HashtagEdge>());
			this.hashtagNodeMetaDataHashMap.put(hashTagKey, hashtagNode);
			this.hashtagIDarrayList.add(hashtagNode);
			
			totalNodesNumber++;
		}
	}
	
	public void addNode(String hashtag, Boolean incrementNodeWeight) {
		this.addNode(hashtag);
		if (incrementNodeWeight) {
			this.getHashtagNode(hashtag).incrementTotalTweetNumberWithOneHashtag();
			this.totalNumberOfTweets++;
			this.totalNumberOfTweetsWithOneHashtag++;
		}
	}
	
	public void addEgde(String hashtagSource, String hashtagTarget) {
		HashtagNode hashtagNodeSource;
		HashtagNode hashtagNodeTarget;
		String hashTagSourceKey = hashtagSource.toLowerCase();
		String hashTagTargetKey = hashtagTarget.toLowerCase();
		
		if (hashTagSourceKey.equals(hashTagTargetKey)) {
			return;
		}
		
		if (!this.hasNode(hashtagSource)) {
			this.addNode(hashtagSource);
		}
		if (!this.hasNode(hashtagTarget)) {
			this.addNode(hashtagTarget);
		}
		
		hashtagNodeSource = this.hashtagNodeMetaDataHashMap.get(hashTagSourceKey);
		hashtagNodeTarget = this.hashtagNodeMetaDataHashMap.get(hashTagTargetKey);
		
		if (!this.hasEdge(hashTagSourceKey, hashTagTargetKey)) {
			HashtagEdge hashtagEdge = new HashtagEdge(hashtagNodeSource, hashtagNodeTarget);
			this.graph.get(hashTagSourceKey).add(hashtagEdge);
		}
		if (!this.hasEdge(hashTagTargetKey, hashTagSourceKey)) {
			HashtagEdge hashtagEdge = new HashtagEdge(hashtagNodeTarget, hashtagNodeSource);
			this.graph.get(hashTagTargetKey).add(hashtagEdge);
		}
	}
	
	public void addEgde(String hashtagSource, String hashtagTarget, Boolean incrementEdgeWeight) {
		this.addEgde(hashtagSource, hashtagTarget);
		if (incrementEdgeWeight) {
			HashtagEdge hashtagEdge1 = this.getHashtagEdge(hashtagSource, hashtagTarget);
			HashtagEdge hashtagEdge2 = this.getHashtagEdge(hashtagTarget, hashtagSource);
			hashtagEdge1.incrementNumberOfTweets();
			hashtagEdge2.incrementNumberOfTweets();
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
				nodeJSON.put("name", hashtagNode.getNameWithCase());
				nodeJSON.put("id", hashtagNode.getNodeID());
				nodeJSON.put("radius", computeD3NodeRadius(hashtagNode));
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
						edgeJSON.put("source", hashtagNodeSource.getNodeID());
						edgeJSON.put("target", hashtagNodeTarget.getNodeID());
						edgeJSON.put("weight", computeD3EdgeWeight(hashtagEdge));
						System.out.println("wEdge:" + ((double)hashtagEdge.getNumberOfTweets())/this.totalNumberOfTweets + " | nbTweetsEdge:" + hashtagEdge.getNumberOfTweets() + " | totalTweets:" + this.totalNumberOfTweets);
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
		double minD3StrokeWidth, maxD3StrokeWidth, minimumPossibleTweetFraction, edgeTweetFraction;
		
		minD3StrokeWidth = 0.8;
		maxD3StrokeWidth = 7;
		minimumPossibleTweetFraction = 1.0 / this.totalNumberOfTweets;
		
		edgeTweetFraction = (double)hashtagEdge.getNumberOfTweets()/this.totalNumberOfTweets;
				
		return minD3StrokeWidth + (maxD3StrokeWidth - minD3StrokeWidth) * 
				(edgeTweetFraction-minimumPossibleTweetFraction)/(1-minimumPossibleTweetFraction);
	}
	
	public double computeD3NodeRadius(HashtagNode hashtagNode) {
		double minD3Radius, maxD3Radius, minimumPossibleTweetFraction, nodeTweetFraction;
		
		// minimum radius for D3: 1
		// maximum radius for D3: 15
		minD3Radius = 1.0;
		maxD3Radius = 15.0;
		minimumPossibleTweetFraction = 1.0 / this.totalNumberOfTweets;
		
		nodeTweetFraction = (double)hashtagNode.getTotalTweetNumber()/this.totalNumberOfTweets; 
		
		return minD3Radius + (maxD3Radius - minD3Radius) * 
				(nodeTweetFraction-minimumPossibleTweetFraction)/(1-minimumPossibleTweetFraction);
	}
}
