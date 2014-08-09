package com.ofg.microservice.twitter

import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

import static org.springframework.util.Assert.notNull
import static org.springframework.util.StringUtils.hasText

@TypeChecked
@RestController
class TwitterCollectorController {
    private TwitterCollector collectorWorker

    @Autowired
    TwitterCollectorController(TwitterCollector collectorWorker) {
        this.collectorWorker = collectorWorker
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/{twitterLogin}/{pairId}", produces="application/json", method = RequestMethod.GET)
    void getTweets(@PathVariable String twitterLogin, @PathVariable Long pairId) {
        hasText(twitterLogin); notNull(pairId)
        collectorWorker.collectAndPassToAnalyzers(twitterLogin, pairId)
    }
}