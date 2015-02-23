package tweetvisualization;
// https://github.com/yusuke/twitter4j/tree/master/twitter4j-examples/src/main/java/twitter4j/examples/stream
// https://dev.twitter.com/streaming/overview/request-parameters#track
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import twittercredentials.TwitterCredentials;

public class TweetVisualization {

    public static void main(String[] args) throws TwitterException {
        // Twitter Authentication
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(TwitterCredentials.CONSUMER_KEY)
            .setOAuthConsumerSecret(TwitterCredentials.CONSUMER_SECRET)
            .setOAuthAccessToken(TwitterCredentials.ACCESS_TOKEN)
            .setOAuthAccessTokenSecret(TwitterCredentials.ACCESS_TOKEN_SECRET);

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
//        RawStreamListener listener = new RawStreamListener() {
//            @Override
//            public void onMessage(String rawJSON) {
//                System.out.println(rawJSON);
//            }
//
//            @Override
//            public void onException(Exception ex) {
//                ex.printStackTrace();
//            }
//        };

        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                // Check to confirm if there is a location
                if(status.getGeoLocation() != null) {
                    System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                    System.out.println("GeoLocation: " + status.getGeoLocation());
                    System.out.println("Place: " + status.getPlace().getFullName());
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };      
                
        // Try filter
        FilterQuery tweetFilterQuery = new FilterQuery(); // See 
        String[] keywords = new String[]{"Oscars2015"};
        tweetFilterQuery.track(keywords); // , is OR on keywords
        tweetFilterQuery.locations(new double[][]{new double[]{-119.3665191,32.6915583}, new double[]{-115.0948341,36.2877522 }});
        tweetFilterQuery.language(new String[]{"en"}); // Note that language does not work properly on Norwegian tweets 
        twitterStream.addListener(listener);
     
        twitterStream.filter(tweetFilterQuery);
//        twitterStream.sample();
    }
}
