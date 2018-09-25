package com.manalili.hpQuizKotlin.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Question(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,
        var question: String,
        var choices: MutableMap<Int, String>) {
}