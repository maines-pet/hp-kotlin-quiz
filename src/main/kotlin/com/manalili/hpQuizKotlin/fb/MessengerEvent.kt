package com.manalili.hpQuizKotlin.fb

import com.fasterxml.jackson.annotation.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessengerEvent(
        @JsonProperty("object")
        val _object: String,
        @JsonProperty("entry")
        val entry: List<Event>){
}

class Event(
        val id: String,
        val time: Long,
        val messaging: List<MutableMap<String, Any>>
) {

//    @JsonSetter("messaging")
//    fun setMessaging(value: Any) {
//        this.messaging.add(value.toString().also{println("kevin p " + it)})
//        return
//    }

//    @JsonAnySetter
//    fun set(key: String, value: Any){
//        this.messaging = mutableMapOf(key to value.toString())
//    }
    override fun toString(): String {
        return "Event(id='$id', time=$time, messaging=$messaging)"
    }
}

