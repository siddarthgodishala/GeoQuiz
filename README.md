# Geo Quiz

A Kotlin Android geography quiz using XML layouts and two Activities.

## Open and run
Open this directory in Android Studio, allow Gradle to sync, select an Android device (Android 7.0 or newer), and run the app configuration.

## How to play
Answer eight geography statements using TRUE or FALSE. Each answer displays a correct/incorrect Toast, and the first answer is locked to keep scoring consistent. NEXT cycles through the questions; the selector also allows jumping to a question. The running score becomes a final score when all eight are answered. Review my answers opens a separate Activity with explanations for answered questions. Start again asks for confirmation before clearing the attempt.

## Assignment requirements
- XML TextViews and Buttons; Kotlin event listeners.
- Eight true/false geography questions in QuestionDeck.
- Correct/incorrect Toast feedback and persistent answer status.
- NEXT navigation and a question selector.
- Running score and final result.
- ReviewActivity opened through an explicit Intent with a Bundle snapshot.
- onSaveInstanceState preserves the selected question and answers on recreation/rotation.

## Source organization
- QuestionDeck.kt: questions, answer key, explanations.
- QuizAttempt.kt: immutable answers and scoring, independent of Android.
- AttemptStorage.kt: converts progress to/from a Bundle.
- QuizActivity.kt: main screen and button handlers.
- ReviewActivity.kt: read-only review from Intent extras.
- QuizWindow.kt: system-bar spacing.
- res/layout: XML screen layouts.

All handwritten application and test source is Kotlin. XML resources and Gradle wrapper files are also required in an Android project. Progress is retained across rotation; explicitly starting again or beginning a new task starts a new quiz. See VERIFICATION.md for the tested scope. Nothing in this project guarantees a grade or a similarity-check outcome.
