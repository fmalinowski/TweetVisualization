package edu.ucsb.ns202.offline;

import org.json.JSONObject;

import edu.ucsb.ns202.graph.HashtagGraph;
import edu.ucsb.ns202.graph.SortedHashtagGraph;

public class HashtagGraphBuilder {
	
	private MySQLAccessor mySQLAccessor;
	private SortedHashtagGraph sortedHashtagGraph;
	
	public HashtagGraphBuilder() {
		this.sortedHashtagGraph = new SortedHashtagGraph();
		this.mySQLAccessor = new MySQLAccessor(MySQLCredentials.LOGIN, 
				MySQLCredentials.PASSWORD, "tweets");
		this.mySQLAccessor.selectDBTable("tweet_superbowl");
		this.mySQLAccessor.connect();
	}
	
	public SortedHashtagGraph buildGraph() {
		String[] hashtagsArray;
		
		this.mySQLAccessor.retrieveAllHashtags();
		
		while (this.mySQLAccessor.hasNext()) {
			hashtagsArray = this.mySQLAccessor.getHashtags();
			
			if (hashtagsArray != null) {
				this.addEdges(hashtagsArray, sortedHashtagGraph);
			}
		}
			
		this.mySQLAccessor.close();
		
		this.sortedHashtagGraph.sortEdgesByNodePopularity();
		return this.sortedHashtagGraph;
	}
	
	public void addEdges(String[] hashtagsArrayList, HashtagGraph hashtagGraph) {
		String hashtagSource, hashtagTarget;
		
		if (hashtagsArrayList.length == 1) {
			if (hashtagGraph.hasNode(hashtagsArrayList[0])) {
				hashtagGraph.incrementNodeWeight(hashtagsArrayList[0]);
			}
			else {
				hashtagGraph.addNode(hashtagsArrayList[0]);
			}
		}
		else {
			for (int i = 0; i < hashtagsArrayList.length; i++) {
				hashtagSource = hashtagsArrayList[i];
				
				for (int j = i+1; j < hashtagsArrayList.length; j++) {
					hashtagTarget = hashtagsArrayList[j];
					if (hashtagGraph.hasEdge(hashtagSource, hashtagTarget)) {
						hashtagGraph.incrementEdgeWeight(hashtagSource, hashtagTarget);
					}
					else {
						hashtagGraph.addEdge(hashtagSource, hashtagTarget);
					}
				}
			}
		}
	}

}
