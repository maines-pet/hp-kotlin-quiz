package com.manalili.hpQuizKotlin.fb.received

import com.fasterxml.jackson.annotation.JsonProperty

class PostbackMessage(
        @JsonProperty("sender")
        override var sender: Id,

        @JsonProperty("postback")
        val postback: Postback) : MessageReceived(sender){

    override fun toString(): String {
        return "PostbackMessage(sender=$sender, postback=$postback)"
    }
}

class Postback(
        @JsonProperty("title")
        val title: String,

        @JsonProperty("payload")
        val payload: String,

        @JsonProperty("referral")
        val referral: String? = null
) {
    override fun toString(): String {
        return "Postback(title='$title', payload='$payload', referral=$referral)"
    }
}

