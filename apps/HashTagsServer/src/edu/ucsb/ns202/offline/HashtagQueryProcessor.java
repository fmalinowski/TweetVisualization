package edu.ucsb.ns202.offline;
import org.json.JSONException;
import org.json.JSONObject;

import edu.ucsb.ns202.HashtagGraph;

public class HashtagQueryProcessor {
	
	private String hashtag;
	
	public HashtagQueryProcessor(String hashtag) {
		this.hashtag = hashtag;
	}

	public JSONObject query() throws JSONException {
		
		HashtagGraph hashtagGraph = new HashtagGraph();
		hashtagGraph.addEgde("#Node1", "#nODe2");
		hashtagGraph.addEgde("#Node1", "#nODe3");
		hashtagGraph.addEgde("#Node4", "NODE5");
		hashtagGraph.addEgde("#Node4", "NODE6");
		hashtagGraph.addEgde("#Node4", "NODE7");
		hashtagGraph.addEgde("#Node4", "#NODE1");
		
		return hashtagGraph.getNodesAndEdgesAsJSON();
	}

}
