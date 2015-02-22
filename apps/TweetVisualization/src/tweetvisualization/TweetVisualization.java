package tweetvisualization;

import twitter4j.*;

public class TweetVisualization {

    public static void main(String[] args) throws TwitterException {
        // TODO code application logic here
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        RawStreamListener listener = new RawStreamListener() {
            @Override
            public void onMessage(String rawJSON) {
                System.out.println(rawJSON);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        twitterStream.addListener(listener);
        twitterStream.sample();
    }       
}
