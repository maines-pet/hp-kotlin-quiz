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
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

@Service
class GameService(val sendApi: SendService) {
    val playersInQueue = ConcurrentHashMap<String, Player>()
    val activePlayers = ConcurrentHashMap<String, Player>()
    val emitters: CopyOnWriteArrayList<SseEmitter> = CopyOnWriteArrayList()
    var isAcceptingPlayers = AtomicBoolean(false)
//        set(value) {
//            if (value.get()) {
//                field = value
//                addFromQueueToActive()
//                notifyPlayers()
//            }
//        }

    var gameStarted = AtomicBoolean(false)
    var gameEnded = AtomicBoolean(false)


    @Autowired
    lateinit var templateEngine: TemplateEngine

    fun notifyPlayers() {
        this.activePlayers.values.map { it.id }.forEach {
            sendApi.send(SendService.MESSAGES,
                    body = MessageToSend(recipient = it, message = MessageContent("Game is ready.")))
        }
//        this.activePlayers.values.forEach {
//            sendApi.send(SendService.MESSAGES,
//                    body = MessageToSend(recipient = it.id, message = MessageContent("Game is ready.")))
//        }
    }

    private fun addFromQueueToActive() {
        this.activePlayers.putAll(playersInQueue)
        this.playersInQueue.clear()
    }

    //Add to queue if game already started
    fun addPlayer(id: String) {
        if (!this.isAcceptingPlayers.get()) {
            this.playersInQueue.getOrPut(id) { Player(id) }
        } else {
            this.activePlayers.getOrPut(id) { Player(id) }
        }
    }

    private fun transmit(payload: Player? = null, message: String = "") {
        emitters.forEach { emitter ->
            try {
                if (payload == null){
                    emitter.send(message, MediaType.TEXT_PLAIN)
                } else {
                    emitter.send(payload, MediaType.APPLICATION_JSON)
                }
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
        val emitterIterator = emitters.iterator()
        val invalidEmitters = mutableListOf<SseEmitter>()
        while (emitterIterator.hasNext()) {
            val emitter = emitterIterator.next()
            try {
                emitter.send(sseBuild)
            } catch (e: IOException) {
                emitter.complete()
                invalidEmitters.add(emitter)
                e.printStackTrace()
            }
        }
        emitters.removeAll(invalidEmitters)
//        emitters.forEach { emitter ->
//            try {
//                emitter.send(sseBuild)
//            } catch (e: IOException) {
//                emitter.complete()
//                emitters.remove(emitter)
//                e.printStackTrace()
//            }
//        }
    }

    fun update(id: String, exe: () -> Unit) {
        exe()
//        val player = activePlayers[id]!!
        val player = this.getPlayer(id)
        if (player?.readyStatus == true) {
            if (this.isAcceptingPlayers.get()) {
                if (!player.hasJoined) {
                    player.hasJoined = true
                    newPlayerJoining(player)
                } else {
                    transmit(player)
                }
//                } else {
//                    transmit(player)
//                }
            }
        }
    }

    fun addEmitter(emitter: SseEmitter) {
        emitters.add(emitter)
    }

    fun removeEmitter(emitter: SseEmitter) {
        emitters.remove(emitter)
    }

    fun getPlayer(id: String): Player? {
        return playersInQueue[id] ?: activePlayers[id]
    }

    fun startGame() {
        isAcceptingPlayers.set(false)
        this.gameStarted.set(true)
    }

    fun acceptPlayers(){
        isAcceptingPlayers.set(true)
        addFromQueueToActive()
        notifyPlayers()
    }

    fun end() {
        this.gameEnded.set(true)
        this.transmit(message = "end")
    }

    fun winner(): List<Player> {
        return this.activePlayers.values.groupBy { it.points }.maxBy { it.key }?.value!!
    }
}


