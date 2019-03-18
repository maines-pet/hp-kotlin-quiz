package com.manalili.hpQuizKotlin.fb

import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class ProfileInformationService(val fb: FbProperties){

    fun retrieveProfile(psId: String): Profile? {
        val fields = LinkedMultiValueMap<String, String>()
//        fields.addAll("fields", listOf("first_name", "last_name"))
        val uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("graph.facebook.com")
                .path("/v3.1/")
                .path(psId)
                .queryParam("fields", "first_name,last_name")
                .queryParam("access_token", fb.accessToken)
                .build().toUri()
        return RestTemplate().getForObject(uri, Profile::class.java)
    }
}
