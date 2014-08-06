package com.ofg.microservice.twitter


public interface TwitterCollector {
    void collectAndPassToAnalyzers(String twitterLogin, Long pairId)
}