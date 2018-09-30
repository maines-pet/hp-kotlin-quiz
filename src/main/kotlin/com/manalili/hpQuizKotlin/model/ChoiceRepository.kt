package com.manalili.hpQuizKotlin.model

import org.springframework.data.repository.CrudRepository

interface ChoiceRepository: CrudRepository<Choice, Int> {
}