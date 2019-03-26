package com.manalili.hpQuizKotlin.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import kotlin.properties.Delegates

class Player(val id: String,
             var name: String = "He who must not be named",
             var points: Int = 0) {

    @get:JsonIgnore
    var isNameSet: Boolean = false
    @get:JsonIgnore
    var isHouseSorted: Boolean = false

    @get:JsonIgnore
    var house: HogwartsHouse = HogwartsHouse.GRYFFINDOR

    @get:JsonIgnore
    var readyStatus = false
        get() = isHouseSorted && isNameSet

    @get: JsonIgnore
    var hasJoined = false

    fun updateName(newName: String) {
        this.apply {
            name = newName
            isNameSet = true
        }
    }

    fun sortToHouse() {
        this.apply {
            house = HogwartsHouse.sortingHat()
            isHouseSorted = true
        }
    }


    @JsonProperty("house")
    fun houseSerialised() = this.house.shorthand
}

enum class HogwartsHouse(val shorthand: String) {
    SLYTHERIN("sl"),
    GRYFFINDOR("gf"),
    RAVENCLAW("rc"),
    HUFFLEPUFF("hf");

    companion object {

        private final val rand: Random = Random()
        fun sortingHat() = values()[rand.nextInt(values().size)]
    }

}



