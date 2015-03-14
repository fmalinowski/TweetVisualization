package edu.ucsb.ns202.offline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.ucsb.ns202.graph.HashtagGraph;
import edu.ucsb.ns202.graph.HashtagNode;

public class HashtagFrequencyFinder {
	
	private String hashtagStarter;
	private MySQLAccessor mySQLaccessor;
	private HashMap<String, Integer> hashtagFrequencies = new HashMap<String, Integer>();
	
	public HashtagFrequencyFinder(String hashtagStarter, MySQLAccessor mySQLaccessor) {
		this.hashtagStarter = hashtagStarter;
		this.mySQLaccessor = mySQLaccessor;
	}
	
	public void findNMostPopularConnectedHashtags() {
		ArrayList<String> hashtagsArrayList;
		Boolean isHashtagInFrequenciesHashMap;
		
		if (!this.mySQLaccessor.isConnected()) {
			this.mySQLaccessor.connect();
			this.mySQLaccessor.selectDBTable("tweet_superbowl");
		}
		this.mySQLaccessor.queryRelatedHashtags(this.hashtagStarter);

		while (this.mySQLaccessor.hasNext()) {
			hashtagsArrayList = (ArrayList<String>) Arrays.asList(this.mySQLaccessor.getHashtags());
			
			if (hashtagsArrayList != null && hashtagsArrayList.contains(this.hashtagStarter)) {
				
				for (String hashtag : hashtagsArrayList) {
					if (!hashtag.equalsIgnoreCase(this.hashtagStarter)) { // And also is not in visitedHashtagsList
						this.hashtagFrequencies.put(hashtag, this.hashtagFrequencies.get(hashtag) + 1);
					}
				}
			}
		}
		
		List list = new LinkedList(this.hashtagFrequencies.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});
	}

}
