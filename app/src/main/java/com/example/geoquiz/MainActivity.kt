package com.example.geoquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val questions = listOf(
        Question(R.string.q0, true),
        Question(R.string.q1, false),
        Question(R.string.q2, true),
        Question(R.string.q3, false),
        Question(R.string.q4, true),
        Question(R.string.q5, false)
    )
    private var current = 0
    // -1 means unanswered, 0 means false, and 1 means true.
    private var answers = IntArray(questions.size) { -1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        current = savedInstanceState?.getInt("current") ?: 0
        answers = savedInstanceState?.getIntArray("answers") ?: answers
        findViewById<Button>(R.id.true_button).setOnClickListener { answer(true) }
        findViewById<Button>(R.id.false_button).setOnClickListener { answer(false) }
        findViewById<Button>(R.id.next_button).setOnClickListener {
            current = (current + 1) % questions.size
            render()
        }
        findViewById<Button>(R.id.previous_button).setOnClickListener {
            current = (current + questions.size - 1) % questions.size
            render()
        }
        findViewById<Button>(R.id.cheat_button).setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java)
            intent.putExtra(CheatActivity.QUESTION, getString(questions[current].textId))
            intent.putExtra(CheatActivity.ANSWER, questions[current].answer)
            startActivity(intent)
        }
        findViewById<Button>(R.id.restart_button).setOnClickListener {
            answers.fill(-1)
            current = 0
            render()
        }
        render()
    }

    private fun answer(choice: Boolean) {
        if (answers[current] != -1) return
        answers[current] = if (choice) 1 else 0
        Toast.makeText(this, if (choice == questions[current].answer) R.string.correct else R.string.incorrect, Toast.LENGTH_SHORT).show()
        render()
    }

    private fun label(value: Boolean) = getString(if (value) R.string.true_label else R.string.false_label)

    private fun render() {
        val answered = answers.count { it != -1 }
        val score = questions.indices.count { answers[it] != -1 && (answers[it] == 1) == questions[it].answer }
        findViewById<TextView>(R.id.question_text).setText(questions[current].textId)
        findViewById<TextView>(R.id.progress).text = getString(R.string.progress, current + 1, questions.size)
        findViewById<TextView>(R.id.score).text = if (answered == questions.size)
            getString(R.string.final_score, score, questions.size)
        else getString(R.string.score, score, questions.size, answered)
        val available = answers[current] == -1
        findViewById<Button>(R.id.true_button).isEnabled = available
        findViewById<Button>(R.id.false_button).isEnabled = available
        findViewById<TextView>(R.id.feedback).text = if (available) getString(R.string.unanswered)
            else getString(R.string.answered, label(answers[current] == 1), label(questions[current].answer))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("current", current)
        outState.putIntArray("answers", answers)
        super.onSaveInstanceState(outState)
    }
}
