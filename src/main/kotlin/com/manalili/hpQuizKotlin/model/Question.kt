package com.manalili.hpQuizKotlin.model

import javax.persistence.*

@Entity
data class Question(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,
        var question: String,

        @OneToMany
        var choices: List<Choice>) {
//        var choices: MutableMap<String, String>) {


}

@Entity
data class Choice(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,
        val option: String, val value: String)