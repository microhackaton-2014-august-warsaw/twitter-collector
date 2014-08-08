package com.ofg.microservice.twitter

import com.ofg.base.MicroserviceMvcWiremockSpec
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.ofg.infrastructure.base.dsl.WireMockHttpRequestMapper.wireMockPut
import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AcceptanceSpec extends MicroserviceMvcWiremockSpec {
    String pairId = '1'
    String testUserTwitterId = 'jnabrdalik'

    def "should return HTTP 200"() {
        given:
            analyzerRespondsOk()
        expect:
            sendUsernameAndPairId().andExpect(status().isOk())
    }

    def "should send tweets with pairId to analyzer"() {
        given:
            analyzerRespondsOk()
        when:
            sendUsernameAndPairId()
        then:
            wireMock.verifyThat(putRequestedFor(urlEqualTo("/analyzer/api/$pairId")).
                    withRequestBody(containing('[{"extraData":{')).
                    withHeader("Content-Type", matching(MediaType.APPLICATION_JSON.toString())))
    }

    private ResultActions sendUsernameAndPairId() {
        mockMvc.perform(get("/tweets/$testUserTwitterId/$pairId").
                accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
    }

    private analyzerRespondsOk() {
        stubInteraction(wireMockPut("/analyzer/api/$pairId"), aResponse().withStatus(OK.value()))
    }
}
