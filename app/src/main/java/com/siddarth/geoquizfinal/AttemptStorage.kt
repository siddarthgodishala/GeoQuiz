package com.siddarth.geoquizfinal

import android.os.Bundle

/** Shared by saved-instance state and the explicit review Intent. */
object AttemptStorage {
    private const val POSITION = "attempt.position"
    private const val RESPONSES = "attempt.responses"

    fun pack(attempt: QuizAttempt): Bundle = Bundle().apply {
        putInt(POSITION, attempt.selectedIndex)
        putBundle(RESPONSES, Bundle().apply {
            attempt.answers.forEach { (questionId, choice) -> putBoolean(questionId, choice) }
        })
    }

    fun unpack(saved: Bundle?, deck: List<GeographyQuestion>): QuizAttempt {
        if (saved == null) return QuizAttempt()
        val responseBundle = saved.getBundle(RESPONSES)
        val answers = deck.mapNotNull { question ->
            if (responseBundle?.containsKey(question.id) == true) {
                question.id to responseBundle.getBoolean(question.id)
            } else null
        }.toMap()
        return QuizAttempt(saved.getInt(POSITION), answers).select(saved.getInt(POSITION), deck.size)
    }
}
