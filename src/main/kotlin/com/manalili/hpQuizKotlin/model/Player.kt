package com.manalili.hpQuizKotlin.model

import java.util.*

data class Player(val id: String,
                  var name: String = "He/she who must not be named",
                  var points: Int = 0) {
    var isNameSet = false
    var isHouseSorted = false
    var house : HogwartsHouse = HogwartsHouse.GRYFFINDOR

    fun updateName(newName: String) {
        this.apply {
            name = newName
            isNameSet = false
        }
    }

    fun sortToHouse() {
        this.house = HogwartsHouse.sortingHat()
        this.isHouseSorted = true
    }

}

enum class HogwartsHouse(val shorthand: String) {
    SLYTHERIN("sl"),
    GRYFFINDOR("gf"),
    RAVENCLAW("rc"),
    HUFFLEPUFF("hf");

    companion object {

        private final val rand : Random =  Random()
        fun sortingHat()= values()[rand.nextInt(values().size)]
    }

}



