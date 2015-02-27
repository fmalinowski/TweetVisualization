package edu.ucsb.ns202.online;

import java.util.ArrayList;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStream {
	public static boolean hasMoreResults = true;
	private static ConfigurationBuilder cb;
	private static twitter4j.TwitterStream ts;
	private static StatusListener ls;
	private static FilterQuery tweetFilterQuery;
	private static List <Status> tweets = new ArrayList();
	private static List <Status> allTweets = new ArrayList();
	private static Object lock = new Object();
	private static int maxTweets = 5;
	
	
	public static int twitterInit() {
		twitterAuth();
		streamListener();
		return 0;
	}
	
	public static List<Status> twitterSearch(String hashTag, boolean hasMoreResults) {
		String nextSearch = "";
		tweetFilterQuery = new FilterQuery(); 
        String[] keywords = new String[]{hashTag};
        tweetFilterQuery.track(keywords); 
        tweetFilterQuery.language(new String[]{"en"});
        ts.addListener(ls);     
        ts.filter(tweetFilterQuery);        
        try {
        	synchronized (lock) {
                lock.wait();
        	}
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
        
		for (Status tweet : tweets) {
			allTweets.add(tweet);
			System.out.println("------");
			for (int i = 0; i < tweet.getHashtagEntities().length; i++) {
				if (!tweet.getHashtagEntities()[i].getText().equalsIgnoreCase(hashTag.substring(1))) {
					nextSearch = tweet.getHashtagEntities()[i].getText();
					System.out.println("#: " + nextSearch);
				}
			}
		}
		tweets.clear();
        
        if(hasMoreResults) {
        	twitterSearch(nextSearch, true);
        }
        System.out.println("Done. Returning...");
        ts.shutdown();
        return allTweets;
	}
	
	private static void twitterAuth() {	
	    cb = new ConfigurationBuilder();
	    cb.setHttpStreamingReadTimeout(10000); // 10 sec timeout
	    cb.setOAuthConsumerKey(TwitterCredentials.CONSUMER_KEY)
	    	.setOAuthConsumerSecret(TwitterCredentials.CONSUMER_SECRET)
	    	.setOAuthAccessToken(TwitterCredentials.ACCESS_TOKEN)
	    	.setOAuthAccessTokenSecret(TwitterCredentials.ACCESS_TOKEN_SECRET);	
	    ts = new TwitterStreamFactory(cb.build()).getInstance();
	}
	
	private static void streamListener() {
		ls = new StatusListener() {
	        public void onStatus(Status status) {
	        	if(status.getHashtagEntities().length > 1) {
	        		tweets.add(status);
		            System.out.println(tweets.size() + ":" + status.getText());
	        	}  
	            if (tweets.size() > maxTweets) {
	                synchronized (lock) {
	                  lock.notify();
	                }
	                System.out.println("Unlocked");
	              }
	        }
	
	        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
	            System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
	        }
	
	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	            System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
	        }
	
	        public void onScrubGeo(long userId, long upToStatusId) {
	            System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
	        }
	
	        public void onStallWarning(StallWarning warning) {
	            System.out.println("Got stall warning:" + warning);
	        }
	
	        public void onException(Exception ex) {
	            ex.printStackTrace();
	        }
	    };   
	}
	
	
	
}
