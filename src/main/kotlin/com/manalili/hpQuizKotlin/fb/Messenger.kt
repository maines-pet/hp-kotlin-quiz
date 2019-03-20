package com.manalili.hpQuizKotlin.fb

import com.fasterxml.jackson.databind.ObjectMapper
import com.manalili.hpQuizKotlin.fb.received.PostbackMessage
import com.manalili.hpQuizKotlin.fb.received.SimpleMessage
import com.manalili.hpQuizKotlin.fb.send.MessageContent
import com.manalili.hpQuizKotlin.fb.send.MessageToSend
import com.manalili.hpQuizKotlin.fb.send.MessagingType
import com.manalili.hpQuizKotlin.service.GameService
import org.springframework.stereotype.Service

@Service
class Messenger(val sendApi: SendService,
                val profileApi: ProfileInformationService,
                val gameService: GameService) {
    private val mapper = ObjectMapper()

    fun onSimpleMessageReceived(msg: String) {
        val simpleMessage = readIntoObject<SimpleMessage>(msg)
        val sender = gameService.players[simpleMessage.sender.id]!!
        if (sender.needName){
            sender.updateName(simpleMessage.message.text)
            val reply = """Thanks, ${sender.name}"""
            val result = this.sendApi.send(endPoint = SendService.MESSAGES,
                    body = MessageToSend(MessagingType.RESPONSE, sender.id, MessageContent(reply)))
        }
    }

    fun onPostbackMessageReceived(msg: String) {
        val postbackMessage = readIntoObject<PostbackMessage>(msg)
        when (postbackMessage.postback.payload) {
            "get_started" -> getStarted(postbackMessage)
        }
    }

    private fun getStarted(postbackMessage: PostbackMessage) {
        gameService.addPlayer(postbackMessage.sender.id)
        listOf("Before we start",
                "how would you like to be called?")
                .forEach {
                    sendApi.send(
                            endPoint = SendService.MESSAGES,
                            body = MessageToSend(MessagingType.RESPONSE,
                                    postbackMessage.sender.id,
                                    MessageContent(it))
                    )
                }
//        val playerProfile = profileApi.retrieveProfile(postbackMessage.sender.id)
//        if (playerProfile != null) {
//            playerService.playerList.add(Player(playerProfile.id, playerProfile.firstName))
//        }
    }


    private inline fun <reified T> readIntoObject(msg: String): T = mapper.readValue(msg, T::class.java)
}

