package com.manalili.hpQuizKotlin.service

import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Component
class SseComponent {
    val emitters = mutableListOf<SseEmitter>()


    fun addEmitter(e: SseEmitter) {
        emitters.add(e)
    }

    fun emit(any: Any) {
        emitters.forEach { it.send(any) }
    }
}
