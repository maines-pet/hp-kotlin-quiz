package com.manalili.hpQuizKotlin.model

import javax.persistence.*

@Entity
data class Question(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "qId")
        val id: Int? = null,
        val question: String = "",

        @OneToMany(cascade = arrayOf(CascadeType.ALL))
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
        val displayText: String,
        val answer: Boolean,
        @Column(name = "questionIdToChoices")
        val questionId: Int? = null){
    override fun toString() = "id = ${this.id}"
}