package com.manalili.hpQuizKotlin.controller

import com.manalili.hpQuizKotlin.model.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/game")
class GameMasterController(val sessionRepository: GameSessionRepository,
                           val questionRepository: QuestionRepository,
                           val playerService: PlayerService) {

    var index = 0
    val questions = mutableListOf<Question>()
    val players = listOf<Player>()

    //initialise game
    @GetMapping("/welcome")
    fun startGame(model: Model): String{
        questions.addAll(questionRepository.findAll())
        model["current"] = questions[index]
        model["questionCounter"] = index + 1
        model["players"] = playerService.playerList
        return "game/quiz"
    }

    @GetMapping("/nexts")
    @ResponseBody
    fun nextQuestion(): ResponseEntity<Question> {
        return if(++index < questions.size) {
            ResponseEntity(questions[index], HttpStatus.OK)
        } else {
            index = 0
            ResponseEntity(HttpStatus.BAD_GATEWAY)
            //or return null
        }
    }
    @GetMapping("/{id}")
    @ResponseBody
    fun showQuestion(@PathVariable id: Int) = "Your id ${id}"

}