package com.manalili.hpQuizKotlin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.manalili.hpQuizKotlin.model.*
import com.manalili.hpQuizKotlin.service.GameService
import org.jboss.logging.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import kotlin.math.roundToInt

@Controller
@RequestMapping("/game")
class GameMasterController(val sessionRepository: GameSessionRepository,
                           val questionRepository: QuestionRepository,
                           val gameService: GameService) {

    private val logger = Logger.getLogger(GameMasterController::class.java)

    //    private val sseEmitters = ConcurrentHashMap<String, MutableList<SseEmitter>>()
    private val sseEmitters = mutableListOf<SseEmitter>()
    var index = 0
    val questions = mutableListOf<Question>()


    //Landing page
    @GetMapping(path = ["/welcome", "/"])
    fun welcome() = "game/welcome"

    //initialise game
    @GetMapping("/quiz")
    fun startGame(model: Model): String {
        questions.addAll(questionRepository.findAll())
        model["current"] = questions[index]
        model["questionCounter"] = index + 1
        model["players"] = gameService.players.values
        //notify all players that the game has just started
        gameService.notifyPlayers()
        return "game/quiz"
    }

    private fun randomise(): String = String.format("%04d", (Math.random() * 10000).roundToInt())

    fun emitPlayer() {
        sseEmitters.forEach { emitter ->
            try {
                emitter.send(ObjectMapper().writeValueAsString(gameService.players.values.first() ), MediaType.APPLICATION_JSON)
            } catch (e: IOException) {
                emitter.complete()
                sseEmitters.remove(emitter)
                e.printStackTrace()
            }
        }
    }
}