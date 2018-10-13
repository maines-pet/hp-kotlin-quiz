package com.manalili.hpQuizKotlin.fb

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.ui.set
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Controller
class Webhook {

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
        println(event)
        //TODO
        //Validate webhook events
        // https://developers.facebook.com/docs/messenger-platform/webhook#security


        return ResponseEntity.ok("Hello")
    }

//    @PostMapping("/webhook")
//    fun receiveEvents(@RequestBody event: String): ResponseEntity<Any>{
//        println(event)
//        return ResponseEntity.ok("Hello")
//    }

}

object Tokens {
    const val VERIFY_TOKEN = "TESTTOKEN"
    const val SUBSCRIBE_MODE = "subscribe"
}

class Unknown