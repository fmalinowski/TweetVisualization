package edu.ucsb.cs290d.tweet_retriever;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Main {

	public static void main(String[] args) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(TwitterCredentials.CONSUMER_KEY)
		  .setOAuthConsumerSecret(TwitterCredentials.CONSUMER_SECRET)
		  .setOAuthAccessToken(TwitterCredentials.ACCESS_TOKEN)
		  .setOAuthAccessTokenSecret(TwitterCredentials.ACCESS_TOKEN_SECRET);
		
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		
		// PLACE YOUR CODE TO USE TWITTER4J
	}

}
