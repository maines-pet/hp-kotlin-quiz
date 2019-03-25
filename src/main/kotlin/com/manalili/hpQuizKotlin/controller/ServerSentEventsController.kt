package com.manalili.hpQuizKotlin.controller

import com.manalili.hpQuizKotlin.model.Player
import com.manalili.hpQuizKotlin.service.GameService
import com.manalili.hpQuizKotlin.service.SseComponent
import org.jboss.logging.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@Controller
class ServerSentEventsController(val gameService: GameService) {
    private val logger = Logger.getLogger(ServerSentEventsController::class.java)
    private val emitters = gameService.emitters


    @GetMapping("/sse")
    fun sse(): SseEmitter {
        val emitter = SseEmitter(1000000L)
        gameService.apply {
            addEmitter(emitter)
            emitter.onCompletion {
                removeEmitter(emitter)
            }
        }
//
//        emitters.add(emitter)
//        emitter.onCompletion {
//            emitters.remove(emitter)
//        }
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

}