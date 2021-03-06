package com.manalili.hpQuizKotlin.fb

import com.fasterxml.jackson.databind.ObjectMapper
import com.manalili.hpQuizKotlin.fb.received.PostbackMessage
import com.manalili.hpQuizKotlin.fb.received.SimpleMessage
import com.manalili.hpQuizKotlin.service.GameService
import org.springframework.stereotype.Service

@Service
class Messenger(val sendApi: SendService,
                val gameService: GameService) {
    private val mapper = ObjectMapper()

    fun onSimpleMessageReceived(msg: String) {
        val simpleMessage = readIntoObject<SimpleMessage>(msg)
        val sender = gameService.getPlayer(simpleMessage.sender.id)
        if (sender?.isNameSet == false) {
            gameService.update(sender.id) {
                sender.updateName(simpleMessage.message.text)
                sender.sortToHouse()
            }
            val reply =
                    listOf("""Thanks, ${sender.name}.""",
                            """You have been sorted to ${sender.house}.""",
                            "I'll let you know once we're ready.")
            this.sendApi.sendSimpleReply(sender.id, replyList = reply)
        }
    }

    fun onPostbackMessageReceived(msg: String) {
        val postbackMessage = readIntoObject<PostbackMessage>(msg)
        when (postbackMessage.postback.payload) {
            "get_started" -> getStarted(postbackMessage)
        }
    }

    private fun getStarted(postbackMessage: PostbackMessage) {
        val sender = postbackMessage.sender.id
        gameService.addPlayer(sender)
        sendApi.sendSimpleReply(sender, replyList = listOf("Before we start",
                "how would you like to be called?"))
    }


    private inline fun <reified T> readIntoObject(msg: String): T = mapper.readValue(msg, T::class.java)
}

