package com.manalili.hpQuizKotlin.fb

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.ui.set
import org.springframework.web.bind.annotation.RequestParam

@Controller
class Webhook {

    @GetMapping("/webhook")
    fun verification(@RequestParam verification: MultiValueMap<String, String>): ResponseEntity<String>{
        val token: String = verification.getFirst("hub.verify_token")?: ""
        return ResponseEntity(token, HttpStatus.OK)
    }
}