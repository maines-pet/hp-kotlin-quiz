package com.manalili.hpQuizKotlin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.manalili.hpQuizKotlin.model.*
import com.manalili.hpQuizKotlin.service.GameService
import org.jboss.logging.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.roundToInt

@Controller
@RequestMapping("/game")
class GameMasterController(val sessionRepository: GameSessionRepository,
                           val questionRepository: QuestionRepository,
                           val gameService: GameService) {

    private val logger = Logger.getLogger(GameMasterController::class.java)

    var index = AtomicInteger(0)
    val questions = CopyOnWriteArrayList<Question>()


    //Landing page
    @GetMapping(path = ["/welcome", "/"])
    fun welcome() = "game/welcome"

    //initialise game
    @GetMapping("/quiz")
    fun initialise(model: Model): String {
        questions.addAll(questionRepository.findAll())
        model["current"] = questions[index.toInt()]
//        model["questionCounter"] = index + 1
        model["players"] = gameService.players.values
        gameService.gameReady = true
        return "game/quiz"
    }

    @GetMapping("/quiz/next")
    fun nextQuestion(model: Model): String{
        val next = index.addAndGet(1)
        return if (next >= questions.size) {
            "/fragment/frag :: end"
        } else {
            model["current"] = questions[next]
            "game/quiz :: question-fragment"
        }
    }

    private fun randomise(): String = String.format("%04d", (Math.random() * 10000).roundToInt())

}