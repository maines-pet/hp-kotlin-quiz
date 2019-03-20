package com.manalili.hpQuizKotlin.model

data class Player(val id: String,
                  var name: String = "He/she who must not be named",
                  var points: Int = 0) {
    var needName = true

    fun updateName(newName: String) {
        this.apply {
            name = newName
            needName = false
        }
    }
}
