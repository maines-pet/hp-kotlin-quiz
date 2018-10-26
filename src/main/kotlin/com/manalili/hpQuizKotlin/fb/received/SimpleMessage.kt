package com.manalili.hpQuizKotlin.fb.received

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class SimpleMessage(
        @JsonProperty("sender")
        override var sender: Id,

        @JsonProperty("message")
        val message: MessageContent) : MessageReceived(sender){
    override fun toString(): String {
        return "SimpleMessage(sender=$sender, message=$message)"
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class MessageContent(
        @JsonProperty("mid")
        val mid: String,
        @JsonProperty("text")
        val text: String,
        @JsonProperty("attachments")
        val attachments: Attachments? = null,

        @JsonProperty("quick_reply")
        val quickReply: QuickReply? = null

) {
    override fun toString(): String {
        return "MessageContent(mid='$mid', text='$text', attachments=$attachments, quickReply=$quickReply)"
    }
}

class Attachments(val type: String, val payload: String)

class QuickReply(val payload: String)