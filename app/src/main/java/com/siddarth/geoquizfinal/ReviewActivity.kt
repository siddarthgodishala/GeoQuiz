package com.siddarth.geoquizfinal

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.siddarth.geoquizfinal.databinding.ReviewBinding
import com.siddarth.geoquizfinal.databinding.ReviewRowBinding

/** Read-only snapshot of the quiz. Unanswered questions do not reveal solutions. */
class ReviewActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ui = ReviewBinding.inflate(layoutInflater)
        setContentView(ui.root)
        ui.root.keepSystemBarsClear()
        val deck = QuestionDeck.questions
        val attempt = AttemptStorage.unpack(intent.getBundleExtra(REVIEW_SNAPSHOT), deck)
        ui.result.text = getString(if (attempt.isComplete(deck)) R.string.final_label else R.string.score_label,
            attempt.score(deck), deck.size)
        ui.reviewHelp.setText(if (attempt.isComplete(deck)) R.string.review_finished else R.string.review_pending)
        ui.backToQuiz.setOnClickListener { finish() }
        deck.forEachIndexed { index, question ->
            val row = ReviewRowBinding.inflate(layoutInflater, ui.answers, false)
            row.rowStatement.text = getString(R.string.item_number, index + 1, question.statement)
            val choice = attempt.answers[question.id]
            if (choice == null) {
                row.rowStatus.setText(R.string.not_answered)
                row.rowExplanation.visibility = View.GONE
            } else {
                val correct = choice == question.answer
                row.rowStatus.text = getString(R.string.review_status,
                    getString(if (correct) R.string.correct_label else R.string.incorrect_label),
                    answerName(choice), answerName(question.answer))
                row.rowStatus.setTextColor(getColor(if (correct) R.color.accent else R.color.incorrect))
                row.rowExplanation.text = question.explanation
            }
            ui.answers.addView(row.root)
        }
    }

    private fun answerName(choice: Boolean): String =
        getString(if (choice) R.string.true_button else R.string.false_button)

    companion object {
        private const val REVIEW_SNAPSHOT = "review.attemptSnapshot"
        fun intentFor(context: Context, attempt: QuizAttempt): Intent =
            Intent(context, ReviewActivity::class.java).putExtra(REVIEW_SNAPSHOT, AttemptStorage.pack(attempt))
    }
}
