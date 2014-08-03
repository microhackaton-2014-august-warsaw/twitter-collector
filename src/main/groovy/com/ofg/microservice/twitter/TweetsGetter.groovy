package com.ofg.microservice.twitter

import groovy.transform.PackageScope
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.social.twitter.api.Tweet
import org.springframework.social.twitter.api.Twitter
import org.springframework.stereotype.Component

import static org.springframework.util.Assert.notNull
import static org.springframework.util.StringUtils.hasText

@TypeChecked
@Component
@PackageScope class TweetsGetter {
    private Twitter twitter
    private int numberOfTweets

    @Autowired
    TweetsGetter(Twitter twitter, @Value('${numberOfTweets:100}') Integer numberOfTweets) {
        notNull(twitter)
        this.twitter = twitter
        this.numberOfTweets = numberOfTweets
    }

    @Cacheable("tweets")
    Collection<Tweet> getTweets(String twitterLogin) {
        hasText(twitterLogin)
        return twitter.timelineOperations().getUserTimeline(twitterLogin, numberOfTweets).findAll{!it.isRetweet()}
    }
}
