package com.manalili.hpQuizKotlin.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.Repository

interface QuestionRepository : CrudRepository<Question, Int>{
    fun findByQuestion(s: String): Question?
}