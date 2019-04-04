package com.manalili.hpQuizKotlin.controller

import com.manalili.hpQuizKotlin.model.GameSessionRepository
import com.manalili.hpQuizKotlin.model.Question
import com.manalili.hpQuizKotlin.model.QuestionRepository
import com.manalili.hpQuizKotlin.service.GameService
import org.jboss.logging.Logger
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
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
        model["players"] = gameService.activePlayers.values
        gameService.acceptPlayers()
        return "game/quiz"
    }

    @GetMapping("/quiz/next")
    fun nextQuestion(model: Model): String{
        this.gameService.apply {
            if (!gameStarted.get()) {
                startGame()
            }
        }

        return if (index.get() >= questions.size) {
            this.gameService.end()
            questions.clear()
            "fragment/frag :: end"
        } else {
            model["current"] = questions[index.getAndAdd(1)]
            "game/quiz :: question-fragment"
        }
    }

    @GetMapping("/quiz/result")
    fun result(model : Model): String {
        model["winners"] = this.gameService.winner()
        return "fragment/frag :: result"
    }


    private fun randomise(): String = String.format("%04d", (Math.random() * 10000).roundToInt())

}