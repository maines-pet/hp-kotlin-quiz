package com.manalili.hpQuizKotlin.controller

import com.manalili.hpQuizKotlin.model.Player
import com.manalili.hpQuizKotlin.model.SseRequestMessage
import org.jboss.logging.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Controller
class ServerSentEventsController {
    private val emitters = mutableListOf<SseEmitter>()
    private val logger = Logger.getLogger(ServerSentEventsController::class.java)

    @GetMapping("/sse")
    fun sse(): SseEmitter {
        val emitter = SseEmitter(1000000L)
        emitters.add(emitter)
        emitter.onCompletion {
            emitters.remove(emitter)
        }
        return emitter
    }
//
//    @ResponseBody
//    @PostMapping("/sseresponse")
//    fun establishSse(input: SseRequestMessage): SseRequestMessage {
//        logger.debug(input)
//
//        emitters.forEach { emitter ->
//            try {
//                emitter.send(input, MediaType.APPLICATION_JSON)
//            } catch (e: IOException) {
//                emitter.complete()
//                emitters.remove(emitter)
//                e.printStackTrace()
//            }
//        }
//        return input
//    }

    fun updateObservers(players: List<Player>) {
        emitters.forEach { emitter ->
            try {
                emitter.send(players, MediaType.APPLICATION_JSON)
            } catch (e: IOException) {
                emitter.complete()
                emitters.remove(emitter)
                e.printStackTrace()
            }
        }
    }
}