package com.manalili.hpQuizKotlin.fb

import com.manalili.hpQuizKotlin.fb.send.MessageContent
import com.manalili.hpQuizKotlin.fb.send.MessageToSend
import com.manalili.hpQuizKotlin.fb.send.MessagingType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import sun.plugin2.message.Message
import java.awt.TrayIcon

@Service
class SendService(val fb: FbProperties) {

    //General API for sending POST Request to fb messenger
    fun send(endPoint: String, psId: String = "/me", body: Any): ResponseEntity<String> {
        val uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("graph.facebook.com")
                .path("/v3.1")
                .path(psId)
                .path(endPoint)
                .queryParam("access_token", fb.accessToken)
                .build().toUri()
        return RestTemplate().postForEntity(uri, body, String::class.java)
    }

    fun sendSimpleReply(id: String, singleReply: String = "", replyList: List<String> = listOf()) {
        replyList.plus(singleReply).forEach {
            if(it.isNotEmpty()){
                this.send(SendService.MESSAGES,
                        body = MessageToSend(MessagingType.RESPONSE, recipient = id,
                                message = MessageContent(it)))
            }
        }
    }


    companion object {
        val MESSAGES = "/messages"
        val MESSENGER_PROFILE = "/messenger_profile"
    }
}
