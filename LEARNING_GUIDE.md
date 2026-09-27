# Learn this project

1. Open res/layout/quiz.xml. Find the TextView named statement and the two answer Buttons. XML describes the arrangement, dimensions and appearance. The IDs let Kotlin address these views.
2. Open QuestionDeck.kt. Each GeographyQuestion contains a stable ID, category, statement, Boolean answer and explanation. true and false are Boolean values, not strings.
3. Open QuizAttempt.kt. The map connects a question ID to the user's Boolean answer. An absent ID means unanswered; a stored false means the user selected FALSE. submit returns a new attempt and rejects repeated answers.
4. Read QuizActivity.onCreate. View Binding connects XML views to Kotlin properties. A click listener runs when the user taps a button.
5. Follow answerCurrentQuestion. It gets the current question, saves the first answer, compares it with the answer key, displays a Toast and redraws the score.
6. Follow show. It derives all displayed values from the current attempt. Score is computed from saved answers instead of incrementing a separate counter that could become inconsistent.
7. Follow ReviewActivity.intentFor. The explicit Intent names the destination Activity. AttemptStorage puts the current answers in its extras; the second Activity reads that snapshot.
8. Read onSaveInstanceState. Android may recreate the Activity when the screen rotates. A Bundle preserves the current index and answers, and onCreate restores them.

Try these exercises locally before submission: add a ninth question with a unique ID; predict the score after one wrong answer; rotate after answering FALSE; explain why repeated answers cannot earn more points. Run the tests after a change. Only submit work you understand and follow your instructor's rules for acknowledging assistance.
