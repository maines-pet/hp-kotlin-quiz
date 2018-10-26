package com.manalili.hpQuizKotlin.fb

import org.springframework.stereotype.Component
import org.springframework.web.client.HttpStatusCodeException

@Component
class MessengerProfile(val sendApi: SendService) {

    fun setupGreeting(locale: String, text: String){
        //TODO check if accesstoken is set
//        if(fb.accessToken.isEmpty())

        val body = WelcomeGreeting(mutableListOf(GreetingLocale(locale, text)))
        try {
            sendApi.send(SendService.MESSENGER_PROFILE, body)
        } catch (e: HttpStatusCodeException){
            println(e.responseBodyAsString)
        }
    }

    fun setupGetStarted(payload: String){
        val body = mapOf("get_started" to mapOf("payload" to payload))
        sendApi.send(SendService.MESSENGER_PROFILE, body)
    }
}
