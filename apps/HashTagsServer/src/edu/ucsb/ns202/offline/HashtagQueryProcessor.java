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

import edu.ucsb.ns202.HashtagGraph;

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
		
//		HashtagGraph hashtagGraph = new HashtagGraph();
//		hashtagGraph.addEgde("#Node1", "#nODe2");
//		hashtagGraph.addEgde("#Node1", "#nODe3");
//		hashtagGraph.addEgde("#Node4", "NODE5");
//		hashtagGraph.addEgde("#Node4", "NODE6");
//		hashtagGraph.addEgde("#Node4", "NODE7");
//		hashtagGraph.addEgde("#Node4", "#NODE1");
		
		return hashtagGraph.getNodesAndEdgesAsJSON();
	}
	
	public void addEdges(String[] hashtagsArrayList, HashtagGraph hashtagGraph) {
		String hashtagSource, hashtagTarget;
		
		if (hashtagsArrayList.length == 1) {
			hashtagGraph.addNode(hashtagsArrayList[0]);
		}
		else {
			System.out.println("-----");
			for (int i = 0; i < hashtagsArrayList.length; i++) {
				hashtagSource = hashtagsArrayList[i];
				
				System.out.println(hashtagSource);
				
				for (int j = i+1; j < hashtagsArrayList.length; j++) {
					hashtagTarget = hashtagsArrayList[j];
					hashtagGraph.addEgde(hashtagSource, hashtagTarget);
					System.out.println("EDGE: S:" + hashtagSource + "|T:" + hashtagTarget);
				}
			}
		}
	}

}
