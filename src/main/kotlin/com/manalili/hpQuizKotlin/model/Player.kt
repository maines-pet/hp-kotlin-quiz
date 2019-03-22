package com.manalili.hpQuizKotlin.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import kotlin.properties.Delegates

class Player(val id: String,
             var name: String = "He/she who must not be named",
             var points: Int = 0) {
    //    var isNameSet = false

    @get:JsonIgnore
    var isNameSet: Boolean by Delegates.observable(false) { _, _, _ ->
        this.ready()
    }
    //    var isHouseSorted = false
    @get:JsonIgnore
    var isHouseSorted: Boolean by Delegates.observable(false) { _, _, _ ->
        this.ready()
    }
    @get:JsonIgnore
    var house: HogwartsHouse = HogwartsHouse.GRYFFINDOR
    @get:JsonIgnore
    var isReady = false

    fun updateName(newName: String) {
        this.apply {
            name = newName
            isNameSet = true
            ready()
        }
    }

    fun sortToHouse() {
        this.apply {
            house = HogwartsHouse.sortingHat()
            isHouseSorted = true
            ready()
        }
    }

    private fun ready() {
        this.apply {
            if (isNameSet && isHouseSorted) {
                isReady = true
            }
        }
    }

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



