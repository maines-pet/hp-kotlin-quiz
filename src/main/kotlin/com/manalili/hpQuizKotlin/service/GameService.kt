package com.manalili.hpQuizKotlin.service

import com.manalili.hpQuizKotlin.controller.ServerSentEventsController
import com.manalili.hpQuizKotlin.fb.SendService
import com.manalili.hpQuizKotlin.fb.send.MessageContent
import com.manalili.hpQuizKotlin.fb.send.MessageToSend
import com.manalili.hpQuizKotlin.model.Player
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class GameService(val sendApi: SendService,
                  val sse: ServerSentEventsController) {
    val players = ConcurrentHashMap<String, Player>()

    fun notifyPlayers() {
        this.players.values.forEach {
            sendApi.send(SendService.MESSAGES,
                    body = MessageToSend(recipient = it.id, message = MessageContent("Game is ready")))
        }
    }

    fun addPlayer(id: String) {
        players.getOrPut(id) { Player(id) }
    }



    fun checkIfReady() {
        if (players.values.all { it.isReady }) {
            sse.updateObservers(players.values.toList())
        }
    }
}


