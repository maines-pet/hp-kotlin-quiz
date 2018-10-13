package com.manalili.hpQuizKotlin.fb

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessengerEvent(
        @JsonProperty("object")
        val _object: String,
        @JsonProperty("entry")
        val entry: List<Event>){
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Event(
        val id: String,
        val time: Long
){
}
