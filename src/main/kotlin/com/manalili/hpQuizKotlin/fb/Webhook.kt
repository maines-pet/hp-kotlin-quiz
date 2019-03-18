package com.manalili.hpQuizKotlin.fb

import com.fasterxml.jackson.databind.ObjectMapper
import com.manalili.hpQuizKotlin.fb.received.MessageContent
import com.manalili.hpQuizKotlin.fb.received.MessageReceived
import com.manalili.hpQuizKotlin.fb.received.PostbackMessage
import com.manalili.hpQuizKotlin.fb.received.SimpleMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Controller
class Webhook(val sendApi: SendService, val messenger: Messenger) {

    @GetMapping("/webhook")
    fun verification(@RequestParam verification: Map<String, String>): ResponseEntity<String> {

        return if (verification["hub.mode"] == Tokens.SUBSCRIBE_MODE
                && verification["hub.verify_token"] == Tokens.VERIFY_TOKEN) {
            ResponseEntity(verification["hub.challenge"], HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.FORBIDDEN)
        }
    }

    @PostMapping("/webhook")
    fun receiveEvents(@RequestBody event: MessengerEvent): ResponseEntity<Any>{
        event.entry.forEach{
            it.messaging.forEach{payload ->
                processEvent(payload)
            }
        }

        //TODO
        //Validate webhook events
        // https://developers.facebook.com/docs/messenger-platform/webhook#security

        return ResponseEntity.ok("Hello")
    }

    private fun processEvent(event: Any){
        val json = mapper.writeValueAsString(event)
        val jsonNode = mapper.readTree(json)

        when {
            jsonNode["postback"] != null -> messenger.onPostbackMessageReceived(json)
            jsonNode["message"] != null -> messenger.onSimpleMessageReceived(json)
            else -> null
        }
    }
}

object Tokens {
    const val VERIFY_TOKEN = "TESTTOKEN"
    const val SUBSCRIBE_MODE = "subscribe"
}

val mapper: ObjectMapper = ObjectMapper()

