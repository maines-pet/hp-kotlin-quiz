package com.manalili.hpQuizKotlin.fb

import com.fasterxml.jackson.databind.ObjectMapper
import com.manalili.hpQuizKotlin.fb.received.PostbackMessage
import com.manalili.hpQuizKotlin.fb.received.SimpleMessage
import com.manalili.hpQuizKotlin.fb.send.MessageContent
import com.manalili.hpQuizKotlin.fb.send.MessageToSend
import com.manalili.hpQuizKotlin.fb.send.MessagingType
import org.springframework.stereotype.Service

@Service
class Messenger(val sendApi: SendService) {
    private val mapper = ObjectMapper()

    fun onSimpleMessageReceived(msg: String){
        val simpleMessage = readIntoObject<SimpleMessage>(msg)
        println(simpleMessage)
    }

    fun onPostbackMessageReceived(msg: String){
        val postbackMessage = readIntoObject<PostbackMessage>(msg)
        if (postbackMessage.postback.payload == "get_started") {
            val messages = listOf("Let's start playing the game",
                    "Please standby while we wait for other players")
                    .forEach {
                        sendApi.send(SendService.MESSAGES,
                                MessageToSend(MessagingType.RESPONSE,
                                postbackMessage.sender.id,
                                MessageContent(it))
                        )
                    }
        }
    }
    private inline fun<reified T> readIntoObject(msg: String) : T = mapper.readValue(msg, T::class.java)
}

