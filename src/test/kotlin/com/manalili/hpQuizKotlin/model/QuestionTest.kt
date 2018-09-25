package com.manalili.hpQuizKotlin.model

import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase
import org.hibernate.testing.transaction.TransactionUtil.doInHibernate
import org.junit.Test



import org.junit.Assert.*

class QuestionTest: BaseCoreFunctionalTestCase() {

    @Test
    fun persist_question_entity_thenFound() {
        doInHibernate(({this.sessionFactory()}), {session ->
            val questionToSave = Question(1, "How", mutableMapOf(Pair(1, "Two")))
            session.persist(questionToSave)
            val questionFound = session.find(Question::class.java, questionToSave.id)
            session.refresh(questionFound)

            assertTrue(questionToSave == questionFound)
        })

    }
}