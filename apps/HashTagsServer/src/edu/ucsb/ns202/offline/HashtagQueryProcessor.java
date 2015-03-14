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

import edu.ucsb.ns202.graph.HashtagGraph;

public class HashtagQueryProcessor {
	
	private String hashtag;
	private MySQLAccessor mySqlAccessor;
	
	public HashtagQueryProcessor(String hashtag) {
		this.hashtag = hashtag;
		
		this.mySqlAccessor = new MySQLAccessor(MySQLCredentials.LOGIN, 
				MySQLCredentials.PASSWORD, "tweets");
	}

	public JSONObject query() {
		String[] hashtagsArray;
		HashtagGraph hashtagGraph = new HashtagGraph();
		
		this.mySqlAccessor.selectDBTable("tweet_superbowl");
		this.mySqlAccessor.connect();
		this.mySqlAccessor.queryRelatedHashtags(this.hashtag);
		
		while (this.mySqlAccessor.hasNext()) {
			hashtagsArray = this.mySqlAccessor.getHashtags();
			
			if (hashtagGraph.getCountOfNodes() > 80) {
				break;
			}
			
			if (hashtagsArray != null) {
				this.addEdges(hashtagsArray, hashtagGraph);
			}
		}
		
		System.out.println("DONE");
			
		this.mySqlAccessor.close();
		
		return hashtagGraph.getNodesAndEdgesAsJSON();
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
			System.out.println("-----");
			for (int i = 0; i < hashtagsArrayList.length; i++) {
				hashtagSource = hashtagsArrayList[i];
				
				System.out.println(hashtagSource);
				
				for (int j = i+1; j < hashtagsArrayList.length; j++) {
					hashtagTarget = hashtagsArrayList[j];
					if (hashtagGraph.hasEdge(hashtagSource, hashtagTarget)) {
						hashtagGraph.incrementEdgeWeight(hashtagSource, hashtagTarget);
					}
					else {
						hashtagGraph.addEdge(hashtagSource, hashtagTarget);
					}
//					System.out.println("EDGE: S:" + hashtagSource + "|T:" + hashtagTarget);
				}
			}
		}
	}

}
