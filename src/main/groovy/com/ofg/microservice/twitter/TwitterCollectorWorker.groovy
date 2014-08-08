package com.ofg.microservice.twitter

import com.ofg.infrastructure.discovery.ServiceResolver
import groovy.transform.PackageScope
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.social.twitter.api.Tweet
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.MediaType.parseMediaType

@TypeChecked
@Component
@PackageScope class TwitterCollectorWorker implements TwitterCollector  {

    public static final String TWITTER_PLACES_ANALYZER_CONTENT_TYPE_HEADER = 'vnd.com.ofg.twitter-places-analyzer.v1+json'
    public static final MediaType TWITTER_PLACES_ANALYZER_MEDIA_TYPE = new MediaType('application', TWITTER_PLACES_ANALYZER_CONTENT_TYPE_HEADER)

    private TweetsGetter tweetsGetter
    private RestTemplate restTemplate = new RestTemplate()
    private ServiceResolver serviceResolver

    @Autowired
    TwitterCollectorWorker(TweetsGetter tweetsGetter, ServiceResolver serviceResolver) {
        this.tweetsGetter = tweetsGetter
        this.serviceResolver = serviceResolver
    }

    @Async
    void collectAndPassToAnalyzers(String twitterLogin, Long pairId) {
        Collection<Tweet> tweets = tweetsGetter.getTweets(twitterLogin)
        String analyzerUrl = serviceResolver.getUrl('analyzer').get()
        restTemplate.put("$analyzerUrl/api/{pairId}", createEntity(tweets), pairId)
    }

    private HttpEntity<Object> createEntity(Object object) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(TWITTER_PLACES_ANALYZER_MEDIA_TYPE)
        return new HttpEntity<Object>(object, headers);
    }
}