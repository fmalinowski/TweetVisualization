package edu.ucsb.ns202;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

public class HashtagGraph {
	
	private class HashtagNode {
		int nodeID;
		
		public HashtagNode(int nodeID) {
			this.nodeID = nodeID;
		}
	}
	
	private HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
	private HashMap<String, String> hashtagWithCaseMapper = new HashMap<String, String>();
	private HashMap<String, HashtagNode> hashtagMetaDataHashMap = new HashMap<String, HashtagNode>();
	private ArrayList<String> hashtagIDarrayList = new ArrayList<String>();
	private int totalNodesNumber = 0;

	public void addNode(String hashtag) {
		String hashTagKey = hashtag.toLowerCase();
		if (!this.graph.containsKey(hashTagKey)) {
			this.graph.put(hashTagKey, new ArrayList<String>());
			hashtagWithCaseMapper.put(hashTagKey, hashtag);
			hashtagMetaDataHashMap.put(hashTagKey, new HashtagNode(totalNodesNumber));
			hashtagIDarrayList.add(hashTagKey);
			
			totalNodesNumber++;
		}
	}
	
	public void addEgde(String hashtagSource, String hashtagTarget) {
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
		
		if (!this.graph.get(hashTagSourceKey).contains(hashTagTargetKey)) {
			this.graph.get(hashTagSourceKey).add(hashTagTargetKey);
		}
		if (!this.graph.get(hashTagTargetKey).contains(hashTagSourceKey)) {
			this.graph.get(hashTagTargetKey).add(hashTagSourceKey);
		}
	}
	
	public Boolean hasNode(String hashtag) {
		return this.graph.containsKey(hashtag.toLowerCase());
	}
	
	public Boolean hasEdge(String hashtagSource, String hashtagTarget) {
		String hashTagSourceKey = hashtagSource.toLowerCase();
		String hashTagTargetKey = hashtagTarget.toLowerCase();
				
		ArrayList<String> nodesList = this.graph.get(hashTagSourceKey);
		
		if(nodesList != null) {
			return nodesList.contains(hashTagTargetKey);
		}
		
		return false;
	}
	
	public int getCountOfEdgesFromSource(String hashtag) {
		String hashTagSourceKey = hashtag.toLowerCase();
		ArrayList<String> nodesList = this.graph.get(hashTagSourceKey);
		
		if (nodesList == null) {
			return 0;
		}
		return nodesList.size();
	}
	
	public int getCountOfNodes() {
		return this.graph.size();
	}

	public ArrayList<String> getNodes() {
		return new ArrayList<String>(this.hashtagWithCaseMapper.values());
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
		
		for (String hashtagKey : this.hashtagIDarrayList) {
			nodeMetaData = this.hashtagMetaDataHashMap.get(hashtagKey);
			hashtagWithCase = hashtagWithCaseMapper.get(hashtagKey);
			
			nodeJSON = new JSONObject();
			try {
				nodeJSON.put("name", hashtagWithCase);
				nodeJSON.put("id", nodeMetaData.nodeID);
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
		HashtagNode sourceNodeMetaData;
		HashtagNode targetNodeMetaData;
		ArrayList<String> edgesList;
		
		for (String hashtagSource : this.graph.keySet()) {
			edgesList = this.graph.get(hashtagSource);
			sourceNodeMetaData = this.hashtagMetaDataHashMap.get(hashtagSource);
			
			for (String hashtagTarget : edgesList) {
				targetNodeMetaData = this.hashtagMetaDataHashMap.get(hashtagTarget);
				
				if (sourceNodeMetaData.nodeID < targetNodeMetaData.nodeID) {
					edgeJSON = new JSONObject();
					try {
						edgeJSON.put("source", sourceNodeMetaData.nodeID);
						edgeJSON.put("target", targetNodeMetaData.nodeID);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					edgesJSON.put(edgeJSON);
				}
			}
		}
		return edgesJSON;
	}
}
