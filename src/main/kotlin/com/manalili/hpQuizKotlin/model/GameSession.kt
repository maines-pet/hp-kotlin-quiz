package com.manalili.hpQuizKotlin.model

import javax.persistence.*

@Entity
class GameSession(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Int? = null
)