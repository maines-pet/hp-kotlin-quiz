package com.manalili.hpQuizKotlin

import com.manalili.hpQuizKotlin.model.Choice
import com.manalili.hpQuizKotlin.model.ChoiceRepository
import com.manalili.hpQuizKotlin.model.Question
import com.manalili.hpQuizKotlin.model.QuestionRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.beans

@SpringBootApplication
class HpQuizKotlinApplication

fun main(args: Array<String>) {
//    runApplication<HpQuizKotlinApplication>(*args)
    SpringApplicationBuilder()
            .sources(HpQuizKotlinApplication::class.java)
            .initializers(beans {
                bean {
                    ApplicationRunner{
                        val questionRepository = ref<QuestionRepository>()
                        val choiceRepository = ref<ChoiceRepository>()
                        val q = Question(question = "Hello there")
                        val listOfChoices = listOf(Choice(displayText = "1", answer = true))
                        q.choice = listOfChoices
                        questionRepository.save(q)
//                        val listOfChoices = choiceRepository.save(Choice(displayText = "a", answer = true, question = q))
//                        arrayOf("Q1", "Q2","Q3").map { questionRepository.save(Question(question = it)) }
                        println(questionRepository.findAll())
                    }
                }
            })
            .run(*args)
}
