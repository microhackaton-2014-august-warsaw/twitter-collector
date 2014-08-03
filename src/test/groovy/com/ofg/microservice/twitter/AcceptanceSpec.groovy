package com.ofg.microservice.twitter
import com.ofg.base.MvcWiremockIntegrationSpec
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.ofg.base.dsl.HttpRequestMapper.httpPut
import static com.ofg.base.dsl.StubbedHttpResponseBuilder.okResponse
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AcceptanceSpec extends MvcWiremockIntegrationSpec {
    String pairId = '1'
    String testUserTwitterId = 'jnabrdalik'

    def "should return HTTP 200"() {
        given:
            correlatorRespondsOk()
        expect:
            sendUsernameAndPairId().andExpect(status().isOk())
    }

    def "should send tweets with pairId to correlator"() {
        given:
            correlatorRespondsOk()
        when:
            sendUsernameAndPairId()
        then:
            verify(putRequestedFor(urlEqualTo("/$pairId")).
                    withRequestBody(containing('[{"extraData":{')).
                    withHeader("Content-Type", matching(MediaType.APPLICATION_JSON.toString())))
    }

    private ResultActions sendUsernameAndPairId() {
        mockMvc.perform(get("/tweets/$testUserTwitterId/$pairId").
                accept(MediaType.APPLICATION_JSON))
    }

    private correlatorRespondsOk() {
        mockInteraction(httpPut("/$pairId"), okResponse())
    }
}
