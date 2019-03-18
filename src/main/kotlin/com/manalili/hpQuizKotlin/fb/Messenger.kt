package com.manalili.hpQuizKotlin.fb

import com.fasterxml.jackson.databind.ObjectMapper
import com.manalili.hpQuizKotlin.fb.received.PostbackMessage
import com.manalili.hpQuizKotlin.fb.received.SimpleMessage
import com.manalili.hpQuizKotlin.fb.send.MessageContent
import com.manalili.hpQuizKotlin.fb.send.MessageToSend
import com.manalili.hpQuizKotlin.fb.send.MessagingType
import com.manalili.hpQuizKotlin.model.Player
import com.manalili.hpQuizKotlin.model.PlayerService
import org.springframework.stereotype.Service

@Service
class Messenger(val sendApi: SendService, val profileApi: ProfileInformationService, val playerService: PlayerService) {
    private val mapper = ObjectMapper()

    fun onSimpleMessageReceived(msg: String){
        val simpleMessage = readIntoObject<SimpleMessage>(msg)
        println(simpleMessage)
    }

    fun onPostbackMessageReceived(msg: String) {
        val postbackMessage = readIntoObject<PostbackMessage>(msg)
        if (postbackMessage.postback.payload == "get_started") {
            val messages = listOf("Let's start playing the game",
                "Please standby while we wait for other players")
                .forEach {
                    sendApi.send(
                            endPoint = SendService.MESSAGES,
                            body = MessageToSend(MessagingType.RESPONSE,
                            postbackMessage.sender.id,
                            MessageContent(it))
                    )
                }
            val playerProfile = profileApi.retrieveProfile(postbackMessage.sender.id)
            if (playerProfile != null) {
                playerService.playerList.add(Player(playerProfile.id, playerProfile.firstName))
            }
//            return if (playerProfile == null) {
//                null
//            } else {
//                Player(playerProfile.id, playerProfile.firstName)
//            }
        }
    }
    private inline fun<reified T> readIntoObject(msg: String) : T = mapper.readValue(msg, T::class.java)
}

