package com.manalili.hpQuizKotlin

import com.manalili.hpQuizKotlin.fb.MessengerProfile
import com.manalili.hpQuizKotlin.model.Choice
import com.manalili.hpQuizKotlin.model.ChoiceRepository
import com.manalili.hpQuizKotlin.model.Question
import com.manalili.hpQuizKotlin.model.QuestionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.beans

@SpringBootApplication
class HpQuizKotlinApplication


fun main(args: Array<String>) {

    SpringApplicationBuilder()
            .sources(HpQuizKotlinApplication::class.java)
            .initializers(beans {
                bean {
                    ApplicationRunner{
                        //ref essentially autowires the beans
                        val msgr = ref<MessengerProfile>()

                        //update greetings
                        msgr.setupGreeting("default", "welcome tod kotlin")
                        msgr.setupGetStarted("get_started")
                    }
                }
            })
            .run(*args)
}
