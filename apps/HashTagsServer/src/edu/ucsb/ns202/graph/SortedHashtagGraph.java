package edu.ucsb.ns202.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;

public class SortedHashtagGraph extends HashtagGraph {
	
//	protected ArrayList<HashtagNode> popularHashtagNodesArrayList = new ArrayList<HashtagNode>();
	
	public void sortEdgesByNodePopularity() {
		HashtagEdgeComparator hashtagEdgeComparator;
		
		for (Entry<String, ArrayList<HashtagEdge>> entry : this.graph.entrySet()) {
			hashtagEdgeComparator = new HashtagEdgeComparator(entry.getKey());
			Collections.sort(entry.getValue(), hashtagEdgeComparator);
		}
		
		Collections.sort(this.hashtagIDarrayList, new HashtagNodeComparator());
		
		for (int i = 0; i < this.hashtagIDarrayList.size(); i++) {
			this.hashtagIDarrayList.get(i).setNodeID(i);
		}
	}

}
