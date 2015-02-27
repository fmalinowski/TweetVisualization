package edu.ucsb.ns202.online;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterRest {
	private static ConfigurationBuilder cb;
	private static Twitter twitter;

	public static void twitterAuth() {
		cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(TwitterCredentials.CONSUMER_KEY)
				.setOAuthConsumerSecret(TwitterCredentials.CONSUMER_SECRET)
				.setOAuthAccessToken(TwitterCredentials.ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(
						TwitterCredentials.ACCESS_TOKEN_SECRET);
		twitter = new TwitterFactory(cb.build()).getInstance();
	}

	public static void twitterSearch(String hashTag) {
		Query query = new Query(hashTag);
		query.count(100);
		query.lang("en");
		int totalCount = 0;
		int total_tweet_per_hashtag = 300;
		Boolean hasStillResults = true;
		int remainingRequestsBeforeRateLimit = 180;
		int secondsUntilLimitRateReset = 0;
		RateLimitStatus rateLimitStatus;

		while ((totalCount < total_tweet_per_hashtag) && hasStillResults) {
			try {
				QueryResult result = twitter.search(query);
				totalCount += result.getCount();

				rateLimitStatus = result.getRateLimitStatus();
				remainingRequestsBeforeRateLimit = rateLimitStatus
						.getRemaining();
				secondsUntilLimitRateReset = rateLimitStatus
						.getSecondsUntilReset();

				java.util.List<Status> tweets = result.getTweets();

				for (Status tweet : tweets) {
					System.out.println("-----------------");
					if (tweet.getHashtagEntities().length > 1) {
						for (int i = 0; i < tweet.getHashtagEntities().length; i++) {
							if (!tweet.getHashtagEntities()[i].getText()
									.equalsIgnoreCase(hashTag.substring(1))) {
								String printHashTag = tweet
										.getHashtagEntities()[i].getText();
								System.out.println("#:" + printHashTag);
							}
						}
						for (int i = 0; i < tweet.getUserMentionEntities().length; i++) {
							String printMention = tweet
									.getUserMentionEntities()[i].getText();
							System.out.println("@:" + printMention);
						}
					}
				}

				hasStillResults = result.hasNext();
				query = result.nextQuery();
				if (remainingRequestsBeforeRateLimit <= 0) {
					System.out.println("Out of requests");
				}
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			System.out.println("DONE. # Requests left: "
					+ remainingRequestsBeforeRateLimit);
		}
	}

}
