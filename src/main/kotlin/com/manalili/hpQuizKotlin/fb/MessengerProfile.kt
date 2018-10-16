package com.manalili.hpQuizKotlin.fb

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
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

        val body = WelcomeGreeting(mutableListOf(GreetingLocale(locale, text)))
        try {
            println(sendApi(fb.accessToken, "/messenger_profile", body))
        } catch (e: HttpStatusCodeException){
            println(e.responseBodyAsString)
        }
    }

    fun setupGetStarted(payload: String){
        val body = mapOf("get_started" to mapOf("payload" to payload))
        println(sendApi(fb.accessToken, "/messenger_profile", body))
    }
}

fun sendApi(accessToken: String, endPoint: String, body: Any): ResponseEntity<String>{
    val uri = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("graph.facebook.com")
            .path("/v3.1/me")
            .path(endPoint)
            .queryParam("access_token", accessToken)
            .build().toUri()
    return RestTemplate().postForEntity(uri, body, String::class.java)
}