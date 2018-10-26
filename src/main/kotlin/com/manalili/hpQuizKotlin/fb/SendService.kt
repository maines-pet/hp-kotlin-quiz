package com.manalili.hpQuizKotlin.fb

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class SendService(val fb: FbProperties){

    fun send(endPoint: String, body: Any): ResponseEntity<String> {
        val uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("graph.facebook.com")
                .path("/v3.1/me")
                .path(endPoint)
                .queryParam("access_token", fb.accessToken)
                .build().toUri()
        return RestTemplate().postForEntity(uri, body, String::class.java)
    }

    companion object {
        val MESSAGES = "/messages"
        val MESSENGER_PROFILE = "/messenger_profile"
    }
}
