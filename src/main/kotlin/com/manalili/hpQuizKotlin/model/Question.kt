package com.manalili.hpQuizKotlin.model

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
data class Question(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "qId")
        val id: Int? = null,
        val question: String = "",

        @Fetch(FetchMode.JOIN)
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "questionIdToChoices", referencedColumnName = "qId")
        var choice: List<Choice>? = listOf()
) {
    override fun toString(): String {
        return "id = ${this.id} question = ${this.question}"
    }
}


@Entity
data class Choice(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        val displayText: String = "",
        val answer: Boolean = false,
        @Column(name = "questionIdToChoices")
        val questionId: Int? = null) {
    override fun toString() = "id = ${this.id}, displayText = ${this.displayText}, answer = ${this.answer}"
}