package com.siddarth.geoquizfinal

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.siddarth.geoquizfinal.databinding.QuizBinding

class QuizActivity : Activity() {
    private val deck = QuestionDeck.questions
    private var attempt = QuizAttempt()
    private lateinit var ui: QuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = QuizBinding.inflate(layoutInflater)
        setContentView(ui.root)
        ui.root.keepSystemBarsClear()
        attempt = AttemptStorage.unpack(savedInstanceState?.getBundle(SAVED_ATTEMPT), deck)
        configureQuestionPicker()
        ui.chooseTrue.setOnClickListener { answerCurrentQuestion(true) }
        ui.chooseFalse.setOnClickListener { answerCurrentQuestion(false) }
        ui.next.setOnClickListener { show(attempt.next(deck.size)) }
        ui.openReview.setOnClickListener { startActivity(ReviewActivity.intentFor(this, attempt)) }
        ui.restart.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.restart_title)
                .setMessage(R.string.restart_message)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.restart_button) { _, _ -> show(QuizAttempt()) }
                .show()
        }
        show(attempt)
    }

    private fun configureQuestionPicker() {
        val titles = deck.mapIndexed { index, question -> "${index + 1} · ${question.topic}" }
        ui.questionPicker.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, titles)
        ui.questionPicker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != attempt.selectedIndex) show(attempt.select(position, deck.size))
            }
        }
    }

    private fun answerCurrentQuestion(choice: Boolean) {
        val question = deck[attempt.selectedIndex]
        val updated = attempt.submit(question, choice)
        if (updated === attempt) return
        val message = if (choice == question.answer) R.string.correct_toast else R.string.incorrect_toast
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        show(updated)
    }

    private fun show(updated: QuizAttempt) {
        attempt = updated
        val question = deck[attempt.selectedIndex]
        val savedAnswer = attempt.answers[question.id]
        with(ui) {
            questionPicker.setSelection(attempt.selectedIndex)
            category.text = question.topic
            statement.text = question.statement
            score.text = getString(if (attempt.isComplete(deck)) R.string.final_label else R.string.score_label,
                attempt.score(deck), deck.size)
            completion.text = getString(R.string.progress_label, attempt.answeredCount(deck), deck.size)
            completionBar.max = deck.size
            completionBar.progress = attempt.answeredCount(deck)
            completionBar.contentDescription = completion.text
            chooseTrue.isEnabled = savedAnswer == null
            chooseFalse.isEnabled = savedAnswer == null
            answerLock.visibility = if (savedAnswer == null) View.GONE else View.VISIBLE
            feedback.text = if (savedAnswer == null) getString(R.string.unanswered) else {
                val status = if (savedAnswer == question.answer) R.string.saved_correct else R.string.saved_incorrect
                getString(status, getString(if (savedAnswer) R.string.true_button else R.string.false_button))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBundle(SAVED_ATTEMPT, AttemptStorage.pack(attempt))
        super.onSaveInstanceState(outState)
    }

    private companion object { const val SAVED_ATTEMPT = "quiz.savedAttempt" }
}
