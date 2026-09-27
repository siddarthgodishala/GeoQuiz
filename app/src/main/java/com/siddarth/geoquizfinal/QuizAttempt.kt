package com.siddarth.geoquizfinal

/** An immutable attempt: every accepted answer produces a new snapshot. */
data class QuizAttempt(
    val selectedIndex: Int = 0,
    val answers: Map<String, Boolean> = emptyMap()
) {
    fun select(index: Int, count: Int): QuizAttempt {
        require(count > 0)
        return copy(selectedIndex = index.coerceIn(0, count - 1))
    }

    fun next(count: Int): QuizAttempt {
        require(count > 0)
        return copy(selectedIndex = (selectedIndex + 1) % count)
    }

    fun submit(question: GeographyQuestion, choice: Boolean): QuizAttempt =
        if (question.id in answers) this else copy(answers = answers + (question.id to choice))

    fun score(deck: List<GeographyQuestion>): Int =
        deck.count { question -> answers[question.id] == question.answer }

    fun answeredCount(deck: List<GeographyQuestion>): Int = deck.count { it.id in answers }
    fun isComplete(deck: List<GeographyQuestion>): Boolean = answeredCount(deck) == deck.size
}
