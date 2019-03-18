package com.manalili.hpQuizKotlin.model

import org.springframework.data.repository.CrudRepository

interface GameSessionRepository: CrudRepository<GameSession, Int> {
}