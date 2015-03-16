package edu.ucsb.ns202.online;

import org.json.JSONException;
import org.json.JSONObject;

import edu.ucsb.ns202.graph.HashtagGraph;
import edu.ucsb.ns202.graph.SortedHashtagGraph;

public class HashtagQueryProcessor {

	public static SortedHashtagGraph placeHolder = new SortedHashtagGraph();
	private String hashtag;

	public HashtagQueryProcessor(String hashtag) {
		this.hashtag = hashtag;
	}

	public JSONObject query() throws JSONException {

		// JSONObject jsonObject = new JSONObject();
		// jsonObject.put("answer", "You typed: " + this.hashtag);
		// return jsonObject;
		
		placeHolder.sortGraph();
		System.out.println("returning getnodesandedges as JSON");
		return placeHolder.getNodesAndEdgesAsJSON();
	}

}
