package com.siddarth.geoquizfinal

import org.junit.Assert.*
import org.junit.Test

class QuizAttemptTest {
    private val deck = QuestionDeck.questions

    @Test fun onlyFirstAnswerCounts() {
        val first = QuizAttempt().submit(deck[0], true)
        assertSame(first, first.submit(deck[0], false))
        assertEquals(1, first.score(deck))
        assertEquals(1, first.answeredCount(deck))
    }

    @Test fun unansweredFalseQuestionsDoNotEarnPoints() {
        assertEquals(0, QuizAttempt().score(deck))
        val answeredFalse = QuizAttempt().submit(deck[1], false)
        assertEquals(1, answeredFalse.score(deck))
        assertFalse(answeredFalse.isComplete(deck))
    }

    @Test fun outOfOrderAnswersProduceFinalScore() {
        var attempt = QuizAttempt()
        deck.reversed().forEachIndexed { index, question ->
            attempt = attempt.submit(question, if (index == 0) !question.answer else question.answer)
        }
        assertTrue(attempt.isComplete(deck))
        assertEquals(deck.size - 1, attempt.score(deck))
        assertEquals(deck.size, attempt.answeredCount(deck))
    }

    @Test fun nextWrapsWithoutLosingAnswers() {
        val attempt = QuizAttempt().submit(deck[0], true).select(deck.lastIndex, deck.size).next(deck.size)
        assertEquals(0, attempt.selectedIndex)
        assertEquals(true, attempt.answers[deck[0].id])
        assertEquals(deck.size, deck.map { it.id }.distinct().size)
    }
}
