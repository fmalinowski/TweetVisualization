package edu.ucsb.ns202.online;

import org.json.JSONException;
import org.json.JSONObject;

import edu.ucsb.ns202.HashtagGraph;

public class HashtagQueryProcessor {

	public static HashtagGraph placeHolder = new HashtagGraph();
	private String hashtag;

	public HashtagQueryProcessor(String hashtag) {
		this.hashtag = hashtag;
	}

	public JSONObject query() throws JSONException {

		// JSONObject jsonObject = new JSONObject();
		// jsonObject.put("answer", "You typed: " + this.hashtag);
		// return jsonObject;

		return placeHolder.getNodesAndEdgesAsJSON();
	}

}
