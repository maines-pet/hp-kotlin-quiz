package com.manalili.hpQuizKotlin.service

import com.manalili.hpQuizKotlin.fb.SendService
import com.manalili.hpQuizKotlin.fb.send.MessageContent
import com.manalili.hpQuizKotlin.fb.send.MessageToSend
import com.manalili.hpQuizKotlin.model.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

@Service
class GameService(val sendApi: SendService) {
    val players = ConcurrentHashMap<String, Player>()
    val emitters: CopyOnWriteArrayList<SseEmitter> = CopyOnWriteArrayList()
    var gameReady = false
        set(value) {
            if (value) {
                field = value
                notifyPlayers()
            }
        }
    var gameStarted = false


    @Autowired
    lateinit var templateEngine: TemplateEngine

    fun notifyPlayers() {
        this.players.values.forEach {
            sendApi.send(SendService.MESSAGES,
                    body = MessageToSend(recipient = it.id, message = MessageContent("Game is ready.")))
        }
    }

    fun addPlayer(id: String) {
        if (this.gameStarted) throw Exception()
        players.getOrPut(id) { Player(id) }
    }

    fun transmit(payload: Player) {
        emitters.forEach { emitter ->
            try {
                emitter.send(payload, MediaType.APPLICATION_JSON)
            } catch (e: IOException) {
                emitter.complete()
                emitters.remove(emitter)
                e.printStackTrace()
            }
        }
    }

    fun newPlayerJoining(payload: Player) {
        val ctx = Context()
        ctx.setVariable("player", payload)
        //For some reason, data is truncated in client end when new and break lines are present in the HTML fragment
        //Solution is to remove those
        val html = templateEngine.process("game/quiz",
                setOf("player-fragment"), ctx).replace(Regex("[\r\n]|\\s{2,}"), "")

        //customise sse event to pass an event name for sse.js client to capture and be added as an event
        val sseBuild = SseEmitter.event().data(html).name("new-player")
        emitters.forEach { emitter ->
            try {
                emitter.send(sseBuild)
            } catch (e: IOException) {
                emitter.complete()
                emitters.remove(emitter)
                e.printStackTrace()
            }
        }
    }

    fun update(id: String, exe: () -> Unit) {
        exe()
        val player = players[id]!!
        println(player.readyStatus)
        if (player.readyStatus) {
            if (!player.hasJoined) {
                player.hasJoined = true
                newPlayerJoining(player)
                if (this.players.values.all { it.readyStatus }) {
                    gameStart()
                }
            } else {
                transmit(player)
            }
        }
    }

    private fun gameStart() {
       this.gameStarted = true
    }

    fun addEmitter(emitter: SseEmitter) {
        emitters.add(emitter)
    }

    fun removeEmitter(emitter: SseEmitter) {
        emitters.remove(emitter)
    }
}


