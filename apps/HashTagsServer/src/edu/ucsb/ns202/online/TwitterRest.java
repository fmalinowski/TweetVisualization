package edu.ucsb.ns202.online;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
	private static Hashtable hashMap = new Hashtable();

	public static void handleSearch(String searchParam) {
		twitterAuth();
		List<String> searchList = twitterSearch(searchParam);
		for (int i = 0; i < 5; i++) {
			twitterSearch(searchList.get(i));
		}
		System.out.println("------------hashMap------------");
		System.out.println(searchParam + ": " + hashMap.get(searchParam));
		for (int i = 0; i < 5; i++) {
			System.out.println(searchList.get(i) + ": "
					+ hashMap.get(searchList.get(i)));
		}
	}

	private static void twitterAuth() {
		cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(TwitterCredentials.CONSUMER_KEY)
				.setOAuthConsumerSecret(TwitterCredentials.CONSUMER_SECRET)
				.setOAuthAccessToken(TwitterCredentials.ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(
						TwitterCredentials.ACCESS_TOKEN_SECRET);
		twitter = new TwitterFactory(cb.build()).getInstance();
	}

	private static List<String> twitterSearch(String hashTag) {
		Query query = new Query(hashTag);
		query.count(100);
		query.lang("en");
		int index = 0;
		int totalCount = 0;
		int totalTweetsPerHT = 400;
		Boolean hasStillResults = true;
		int remainingRequestsBeforeRateLimit = 180;
		int secondsUntilLimitRateReset = 0;
		RateLimitStatus rateLimitStatus;
		List<String> valuesList = new ArrayList<String>();

		while ((totalCount < totalTweetsPerHT) && hasStillResults) {
			try {
				QueryResult result = twitter.search(query);
				totalCount += result.getCount();

				rateLimitStatus = result.getRateLimitStatus();
				remainingRequestsBeforeRateLimit = rateLimitStatus
						.getRemaining();
				secondsUntilLimitRateReset = rateLimitStatus
						.getSecondsUntilReset();

				List<Status> tweets = result.getTweets();

				for (Status tweet : tweets) {
					if (tweet.getHashtagEntities().length > 1) {
						// System.out.println("-----------------");
						for (int i = 0; i < tweet.getHashtagEntities().length; i++) {
							if (!tweet.getHashtagEntities()[i].getText()
									.equalsIgnoreCase(hashTag)) {
								String printHashTag = tweet
										.getHashtagEntities()[i].getText();
								// System.out
								// .println(index + ": #" + printHashTag);
								valuesList.add(printHashTag);
								index++;
							}
						}
						for (int i = 0; i < tweet.getUserMentionEntities().length; i++) {
							// String printMention = tweet
							// .getUserMentionEntities()[i].getText();
							// System.out.println(index + ": @" + printMention);
							index++;
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
		// Add iteration to hashmap
		hashMap.put(hashTag, valuesList);
		hashMap.put("asd", "asd");
		// System.out.println(hashMap.toString());
		// System.out.println(hashMap.size());
		return valuesList;
	}
}
