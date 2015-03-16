package edu.ucsb.ns202.offline;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import edu.ucsb.ns202.graph.FrontEndHashtagGraph;
import edu.ucsb.ns202.graph.HashtagGraph;
import edu.ucsb.ns202.graph.SortedHashtagGraph;

public class HashtagQueryProcessor {
	
	private String hashtag;
	private SortedHashtagGraph hashtagGraph;
	
	public HashtagQueryProcessor(SortedHashtagGraph hashtagGraph, String hashtag) {
		this.hashtagGraph = hashtagGraph;
		this.hashtag = hashtag;
	}

	public JSONObject query() {
		FrontEndGraphBuilder frontEndGraphBuilder;
		FrontEndHashtagGraph frontEndHashtagGraph;
		int[] nodesPerLevel = {20, 5, 2};
		String desiredHashtag;
		
		// In case we are putting an empty string, we want to look for the most popular
		// hashtag and its links
		desiredHashtag = (String) (this.hashtag.replaceAll(" ", "").length() == 0 ? 
				this.hashtagGraph.getAllNodes().get(0) : this.hashtag);
		
		frontEndGraphBuilder = new FrontEndGraphBuilder(this.hashtagGraph, this.hashtag, nodesPerLevel);
		frontEndHashtagGraph = frontEndGraphBuilder.buildGraph();
		
		return frontEndHashtagGraph.getNodesAndEdgesAsJSON();
	}

}
