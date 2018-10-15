package com.manalili.hpQuizKotlin.fb

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class MessengerProfile() {

    @Autowired
    lateinit var fb: FbProperties

    val mapper = jacksonObjectMapper()

    val restTemplate: RestTemplate = RestTemplate()
    fun setupGreeting(locale: String, text: String){
        //TODO check if accesstoken is set
//        if(fb.accessToken.isEmpty())
        val uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("graph.facebook.com")
                .path("/v3.1/me/messenger_profile")
                .queryParam("access_token", fb.accessToken)
                .build().toUri()
        val body = WelcomeGreeting(mutableListOf(GreetingLocale(locale, text)))
        println(fb.accessToken)
        try {
            println(restTemplate.postForEntity(uri, body, String::class.java))
        } catch (e: HttpStatusCodeException){
            println(e.responseBodyAsString)
        }

    }
}

