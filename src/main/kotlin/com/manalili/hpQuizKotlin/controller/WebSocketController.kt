package com.manalili.hpQuizKotlin.controller

import com.manalili.hpQuizKotlin.model.Greeting
import com.manalili.hpQuizKotlin.model.HelloMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    fun greeting(message: HelloMessage) = Greeting("Hi there, you are chatting")
}