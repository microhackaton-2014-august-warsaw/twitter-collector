package com.ofg.base.dsl

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.UrlMatchingStrategy
import groovy.transform.TypeChecked

import static com.github.tomakehurst.wiremock.client.WireMock.*

@TypeChecked
class HttpRequestMapper {

    static UrlMatchingStrategy withUrl(String path) {
        return urlEqualTo(path)
    }

    static MappingBuilder httpGet(String path) {
        return get(withUrl(path))
    }

    static MappingBuilder httpPost(String path) {
        return post(withUrl(path))
    }

    static MappingBuilder httpPost(String path, String requestBodyPath) {
        return post(withUrl(path)).withRequestBody(equalToXml(new HttpRequestMapper().fromResource(requestBodyPath)))
    }

    static MappingBuilder httpPut(String path) {
        return put(withUrl(path))
    }

    static MappingBuilder httpPut(String path, String requestBodyPath) {
        return put(withUrl(path)).withRequestBody(equalToXml(new HttpRequestMapper().fromResource(requestBodyPath)))
    }

    private String fromResource(String path) {
        return this.getClass().getResource('/' + path).text
    }
}
