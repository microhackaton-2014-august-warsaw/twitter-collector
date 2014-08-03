package com.ofg.base

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.ofg.microservice.config.web.HttpMockServer
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired

@TypeChecked
class MvcWiremockIntegrationSpec extends MvcIntegrationSpec {
    protected WireMock wireMock
    @Autowired HttpMockServer httpMockServer

    void setup() {
        wireMock = new WireMock('localhost', httpMockServer.port())
        wireMock.configureFor("localhost", httpMockServer.port())
        wireMock.resetMappings()
    }

    protected void mockInteraction(MappingBuilder mapping, ResponseDefinitionBuilder response) {
        wireMock.register(mapping.willReturn(response))
    }
}
