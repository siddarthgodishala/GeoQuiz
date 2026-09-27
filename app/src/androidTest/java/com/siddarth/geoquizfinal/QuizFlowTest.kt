package com.siddarth.geoquizfinal

import android.content.pm.ActivityInfo
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuizFlowTest {
    @Test fun completesQuizAndReviewsAnswers() {
        ActivityScenario.launch(QuizActivity::class.java).use {
            QuestionDeck.questions.forEachIndexed { index, question ->
                val choice = if (index == 0) !question.answer else question.answer
                onView(withId(if (choice) R.id.choose_true else R.id.choose_false)).perform(scrollTo(), click())
                if (index != QuestionDeck.questions.lastIndex) onView(withId(R.id.next)).perform(scrollTo(), click())
            }
            onView(withId(R.id.score)).check(matches(withText("Completed · Final score: 7 / 8")))
            onView(withId(R.id.quiz_root)).perform(swipeUp())
            onView(withId(R.id.open_review)).perform(scrollTo(), click())
            onView(withId(R.id.result)).check(matches(withText("Completed · Final score: 7 / 8")))
            onView(withText(QuestionDeck.questions[0].explanation)).check(matches(isDisplayed()))
            pressBack()
            onView(withId(R.id.completion)).check(matches(withText("Answered: 8 / 8")))
        }
    }

    @Test fun rotationAndRecreationRetainFalseAnswerAndSelectedQuestion() {
        ActivityScenario.launch(QuizActivity::class.java).use { scenario ->
            onView(withId(R.id.next)).perform(scrollTo(), click())
            onView(withId(R.id.choose_false)).perform(scrollTo(), click())
            scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
            onView(withId(R.id.statement)).check(matches(withText(QuestionDeck.questions[1].statement)))
            scenario.recreate()
            scenario.onActivity {
                assertEquals(1, it.findViewById<Spinner>(R.id.question_picker).selectedItemPosition)
                assertEquals("Score: 1 / 8", it.findViewById<TextView>(R.id.score).text.toString())
                assertFalse(it.findViewById<Button>(R.id.choose_true).isEnabled)
                assertFalse(it.findViewById<Button>(R.id.choose_false).isEnabled)
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            onView(withId(R.id.completion)).check(matches(withText("Answered: 1 / 8")))
        }
    }

    @Test fun reviewIntentSnapshotSurvivesRecreation() {
        val context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
        val attempt = QuizAttempt().select(1, 8).submit(QuestionDeck.questions[1], false)
        val intent = ReviewActivity.intentFor(context, attempt)
        assertEquals(ReviewActivity::class.java.name, intent.component?.className)
        ActivityScenario.launch<ReviewActivity>(intent).use { scenario ->
            scenario.recreate()
            onView(withId(R.id.result)).check(matches(withText("Score: 1 / 8")))
            onView(withText(QuestionDeck.questions[1].explanation)).perform(scrollTo()).check(matches(isDisplayed()))
        }
    }

    @Test fun restartRequiresConfirmationThenClearsProgress() {
        ActivityScenario.launch(QuizActivity::class.java).use {
            onView(withId(R.id.choose_true)).perform(scrollTo(), click())
            onView(withId(R.id.quiz_root)).perform(swipeUp())
            onView(withId(R.id.restart)).perform(click())
            onView(withId(android.R.id.button2)).perform(click())
            onView(withId(R.id.score)).check(matches(withText("Score: 1 / 8")))
            onView(withId(R.id.quiz_root)).perform(swipeUp())
            onView(withId(R.id.restart)).perform(click())
            onView(withId(android.R.id.button1)).perform(click())
            onView(withId(R.id.completion)).check(matches(withText("Answered: 0 / 8")))
            onView(withId(R.id.choose_true)).check(matches(isEnabled()))
        }
    }
}
