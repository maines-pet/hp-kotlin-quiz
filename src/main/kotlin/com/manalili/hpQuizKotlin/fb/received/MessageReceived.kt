package com.manalili.hpQuizKotlin.fb.received

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class MessageReceived(
        open val sender: Id
) {


    override fun toString(): String {
        return "MessageReceived(sender=$sender)"
    }
}

class Id(@JsonProperty("id") val id: String){
    override fun toString(): String {
        return "Id(id='$id')"
    }
}