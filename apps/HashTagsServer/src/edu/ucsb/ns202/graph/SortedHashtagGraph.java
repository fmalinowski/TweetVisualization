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
		Collections.sort(this.hashtagEdgearrayList, new HashtagEdgeComparator());
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
	
	public JSONArray getNodesAsJSON() {
		this.sortNodeListByDecreasingPopularity();
		return super.getNodesAsJSON();
	}
	
	public JSONArray getEdgesAsJSON() {
		this.sortEdgeListByDecreasingPopularity();
		return super.getEdgesAsJSON();
	}

}
