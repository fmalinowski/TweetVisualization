package edu.ucsb.ns202.online;

import org.json.JSONException;
import org.json.JSONObject;

public class HashtagQueryProcessor {

	private String hashtag;

	public HashtagQueryProcessor(String hashtag) {
		this.hashtag = hashtag;
	}

	public JSONObject query() throws JSONException {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("answer", "You typed: " + this.hashtag);
		return jsonObject;
	}

}
