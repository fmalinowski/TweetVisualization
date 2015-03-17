package edu.ucsb.ns202.graph;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SortedHashtagGraph extends HashtagGraph {
	
//	protected ArrayList<HashtagNode> popularHashtagNodesArrayList = new ArrayList<HashtagNode>();
	
	public void sortEdgesOfANodeByNodePopularity() {
		HashtagEdgesRelatedToNodeComparator hashtagEdgesRelatedComparator;
		
		// We sort the edges for each node by decreasing order of popularity of the nodes (not edge popularity!)
		for (Entry<String, ArrayList<HashtagEdge>> entry : this.graph.entrySet()) {
			hashtagEdgesRelatedComparator = new HashtagEdgesRelatedToNodeComparator(entry.getKey());
			Collections.sort(entry.getValue(), hashtagEdgesRelatedComparator);
		}
	}
	
	public void sortNodeListByDecreasingPopularity() {
		Collections.sort(this.hashtagIDarrayList, new HashtagNodeComparator());
		
		for (int i = 0; i < this.hashtagIDarrayList.size(); i++) {
			this.hashtagIDarrayList.get(i).setNodeID(i);
		}
	}
	
	public void sortEdgeListByDecreasingPopularity() {
		HashtagEdge hashtagEdge;
		Collections.sort(this.hashtagEdgearrayList, new HashtagEdgeComparator());
		
		for (int i = 0; i < this.hashtagEdgearrayList.size(); i++) {
			hashtagEdge = this.hashtagEdgearrayList.get(i); 
			hashtagEdge.setEdgeRank(i);
			this.getHashtagEdge(hashtagEdge.getTarget().getNameWithoutCase(), 
					hashtagEdge.getSource().getNameWithoutCase()).setEdgeRank(i);
		}
	}
	
	public void sortGraph() {
		this.sortEdgesOfANodeByNodePopularity();
		this.sortNodeListByDecreasingPopularity();
		this.sortEdgeListByDecreasingPopularity();
	}
	
	protected double computeD3EdgeWeight(HashtagEdge hashtagEdge) {
		// minimum width-stroke for D3: 0.3 (preferred: 0.8)
		// maximum width-stroke for D3: 7
		double minD3StrokeWidth, maxD3StrokeWidth, minEdgeWeight, maxEdgeWeight;
		double fractionOfMinimumEdgeWeight;
		double edgeTweetFraction, result;
		
		minD3StrokeWidth = 0.8;
		maxD3StrokeWidth = 7.0;
		minEdgeWeight = (double) this.hashtagEdgearrayList.get(this.hashtagEdgearrayList.size()-1).getNumberOfTweetsInvolved();
		maxEdgeWeight = (double) this.hashtagEdgearrayList.get(0).getNumberOfTweetsInvolved();
		fractionOfMinimumEdgeWeight = minEdgeWeight / maxEdgeWeight;
		
		edgeTweetFraction = (double)hashtagEdge.getNumberOfTweetsInvolved()/maxEdgeWeight;
				
		result = minD3StrokeWidth + (maxD3StrokeWidth - minD3StrokeWidth) * 
				(edgeTweetFraction-fractionOfMinimumEdgeWeight)/(1-fractionOfMinimumEdgeWeight);
		
		return new BigDecimal(result).setScale(2, RoundingMode.CEILING).doubleValue();
	}
	
	protected double computeD3NodeRadius(HashtagNode hashtagNode) {
		double minD3Radius, maxD3Radius, minNodeWeight, maxNodeWeight; 
		double fractionOfMinimumNodeWeight;
		double nodeTweetFraction, result;
		
		// minimum radius for D3: 1
		// maximum radius for D3: 15
		minD3Radius = 3.0;
		maxD3Radius = 13.0;
		minNodeWeight = (double) this.hashtagIDarrayList.get(this.hashtagIDarrayList.size()-1).getNumberOfTweetsInvolved();
		maxNodeWeight = (double) this.hashtagIDarrayList.get(0).getNumberOfTweetsInvolved();
		fractionOfMinimumNodeWeight = minNodeWeight / maxNodeWeight;
		
		nodeTweetFraction = (double)hashtagNode.getNumberOfTweetsInvolved() / maxNodeWeight; 
		
		result = minD3Radius + (maxD3Radius - minD3Radius) * 
				(nodeTweetFraction-fractionOfMinimumNodeWeight)/(1-fractionOfMinimumNodeWeight);
		
		return new BigDecimal(result).setScale(2, RoundingMode.CEILING).doubleValue();
	}
	
//	public JSONArray getNodesAsJSON() {
//		this.sortNodeListByDecreasingPopularity();
//		return super.getNodesAsJSON();
//	}
//	
//	public JSONArray getEdgesAsJSON() {
//		this.sortEdgeListByDecreasingPopularity();
//		return super.getEdgesAsJSON();
//	}
	
	@Override
	public JSONObject getNodesAndEdgesAsJSON() {
		JSONObject result = new JSONObject();
		try {
			result.put("nodes", this.getNodesAsJSON());
			result.put("links", this.getEdgesAsJSON());
			addGeneralInfoForJSON(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public JSONArray getNodesAsJSON() {
		JSONArray nodesJSON = new JSONArray();
		JSONObject nodeJSON;
		HashtagNode nodeMetaData;
		String hashtagWithCase;
		
		for (HashtagNode hashtagNode : this.hashtagIDarrayList) {			
			nodeJSON = new JSONObject();
			try {
//				System.out.println("Node name:" + hashtagNode.getNameWithCase() + " | id:" + hashtagNode.getNodeID() + " | NbTweets:" + hashtagNode.getNumberOfTweetsInvolved() + " | totalTweets:" + this.totalNumberOfTweets  + " | radius:" + computeD3NodeRadius(hashtagNode));
				nodeJSON.put("name", hashtagNode.getNameWithCase());
				nodeJSON.put("id", hashtagNode.getNodeID());
				nodeJSON.put("radius", computeD3NodeRadius(hashtagNode));
				nodeJSON.put("type", hashtagNode.getType());
				this.addNodeInfoForJSON(nodeJSON, hashtagNode);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			nodesJSON.put(nodeJSON);
		}
		return nodesJSON;
	}
	
	@Override
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
//						System.out.println("edge source:" + hashtagNodeSource.getNameWithCase() + " | target:" + hashtagNodeTarget.getNameWithCase() + " | nbTweets:" + hashtagEdge.getNumberOfTweetsInvolved() + " | totalTweets:" + this.totalNumberOfTweets + " | weight:" + computeD3EdgeWeight(hashtagEdge));
						edgeJSON.put("source", hashtagNodeSource.getNodeID());
						edgeJSON.put("target", hashtagNodeTarget.getNodeID());
						edgeJSON.put("weight", computeD3EdgeWeight(hashtagEdge));
						this.addEdgeInfoForJSON(edgeJSON, hashtagEdge);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					edgesJSON.put(edgeJSON);
				}
			}
		}
		return edgesJSON;
	}
	
	private void addNodeInfoForJSON(JSONObject jsonObj, HashtagNode hashtagNode) {
		String hashtagKey;
		int degreeOfNode, popularityRank;
		double percentageTotalTweetNb;
		
		hashtagKey = hashtagNode.getNameWithoutCase();
		percentageTotalTweetNb = (double)hashtagNode.getNumberOfTweetsInvolved()/this.totalNumberOfTweets * 100.0;
		popularityRank = this.getHashtagNode(hashtagKey).getNodeID() + 1;
		degreeOfNode = this.graph.get(hashtagKey).size();
		try {
			jsonObj.put("nbOfTweets", hashtagNode.getNumberOfTweetsInvolved());
			jsonObj.put("percentageTotalTweetNb", new BigDecimal(percentageTotalTweetNb).setScale(3, RoundingMode.CEILING).doubleValue());
			jsonObj.put("degree", degreeOfNode);
			jsonObj.put("popularityRank", popularityRank);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void addEdgeInfoForJSON(JSONObject jsonObj, HashtagEdge hashtagEdge) {
		double percentageTotalTweetNb;
		int popularityRank;
		String sourceNodeKey, targetNodeKey;
		
		sourceNodeKey = hashtagEdge.getSource().getNameWithoutCase();
		targetNodeKey = hashtagEdge.getTarget().getNameWithoutCase();
		
		percentageTotalTweetNb = (double)hashtagEdge.getNumberOfTweetsInvolved()/this.totalNumberOfTweets * 100.0;
		popularityRank = this.getHashtagEdge(sourceNodeKey, targetNodeKey).getEdgeRank() + 1;
		try {
			jsonObj.put("nbOfTweets", hashtagEdge.getNumberOfTweetsInvolved());
			jsonObj.put("percentageTotalTweetNb", new BigDecimal(percentageTotalTweetNb).setScale(3, RoundingMode.CEILING).doubleValue());
			jsonObj.put("popularityRank", popularityRank);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void addGeneralInfoForJSON(JSONObject jsonObj) {
		double graphDensity;
		
		graphDensity = 2.0 * this.totalEdgesNumber / (this.totalNodesNumber * (this.totalNodesNumber-1));
		
		try {
			jsonObj.put("activateInfos", true);
			jsonObj.put("totalNbOfTweets", this.totalNumberOfTweets);
			jsonObj.put("totalNbOfNodes", this.totalNodesNumber);
			jsonObj.put("totalNbOfEdges", this.totalEdgesNumber);
			jsonObj.put("density", new BigDecimal(graphDensity).setScale(3, RoundingMode.CEILING).doubleValue());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
