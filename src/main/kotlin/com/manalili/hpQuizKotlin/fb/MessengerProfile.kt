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
    val rawUrl: String = "https://graph.facebook.com/v2.6/me/messenger_profile?access_token="

    fun setupGreeting(locale: String, text: String){

        val uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("graph.facebook.com")
                .path("/v3.1/me/messenger_profile")
                .queryParam("access_token", fb.accessToken)
        val url = "https://graph.facebook.com/v2.6/me/messenger_profile?access_token=" + fb.accessToken
        val welcomeGreeting = WelcomeGreeting(mutableListOf(GreetingLocale(locale, text)))
        try {
//            println(restTemplate.postForEntity(uri.build().toUri(), welcomeGreeting, String::class.java))
            val headers = HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)
//            val settings = mapOf("locale" to locale, "text" to text)

//            val bodyreq = mapper.writeValueAsString(mutableMapOf("greeting" to listOf(settings)))
            val bodyreq = mapper.writeValueAsString(welcomeGreeting)

            val body = HttpEntity<String>(bodyreq, headers)

//            println(restTemplate.postForEntity(url, welcomeGreeting, String::class.java))
            println(body.body)
            println(restTemplate.postForEntity(url, body, String::class.java))


        } catch (e: HttpStatusCodeException){

            println(e.responseBodyAsString)
        }

    }
}

