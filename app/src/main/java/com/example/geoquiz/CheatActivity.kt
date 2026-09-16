package com.example.geoquiz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CheatActivity : AppCompatActivity() {
    private var revealed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cheat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        revealed = savedInstanceState?.getBoolean("revealed") ?: false
        findViewById<TextView>(R.id.question_text).text = intent.getStringExtra(QUESTION)
        findViewById<Button>(R.id.reveal_button).setOnClickListener { revealed = true; render() }
        findViewById<Button>(R.id.back_button).setOnClickListener { finish() }
        render()
    }
    private fun render() {
        findViewById<TextView>(R.id.answer).text = if (revealed)
            getString(R.string.answer_format, getString(if (intent.getBooleanExtra(ANSWER, false)) R.string.true_label else R.string.false_label))
        else getString(R.string.hidden_answer)
        findViewById<Button>(R.id.reveal_button).isEnabled = !revealed
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("revealed", revealed)
        super.onSaveInstanceState(outState)
    }
    companion object {
        const val QUESTION = "com.example.geoquiz.question"
        const val ANSWER = "com.example.geoquiz.answer"
    }
}
