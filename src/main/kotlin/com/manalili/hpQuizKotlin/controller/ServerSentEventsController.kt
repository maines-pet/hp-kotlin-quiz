package com.manalili.hpQuizKotlin.controller

import com.manalili.hpQuizKotlin.service.GameService
import org.jboss.logging.Logger
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Controller
class ServerSentEventsController(val gameService: GameService) {

    @GetMapping("/sse")
    fun sse(): SseEmitter {
        val emitter = SseEmitter(1000000L)
        gameService.apply {
            addEmitter(emitter)
            emitter.onCompletion {
                removeEmitter(emitter)
            }
        }

        return emitter
    }

}