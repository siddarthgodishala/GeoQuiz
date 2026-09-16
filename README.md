# GeoQuiz — Ocean Atlas

Android geography quiz built with Kotlin and XML layouts.

## Open and run
1. Choose **Code → Download ZIP** on GitHub and extract the downloaded archive, or clone this repository.
2. In Android Studio, choose **Open** and select the folder containing `settings.gradle.kts`.
3. Let Gradle sync and install any requested Android SDK components (compile SDK 37).
4. Select an Android emulator or device running Android 7.0 (API 24) or later and press **Run**.

The source files, resources, manifest, Gradle configuration, and wrapper are included directly in this repository. `GeoQuiz.zip` is an additional project archive. Machine-specific settings and build caches are excluded.

## Features
- Six geography true/false questions with correct/incorrect Toast feedback.
- Next and previous navigation; each question scores only once.
- Running score, final result after all questions are answered, and restart.
- Separate Cheat Activity opened with an explicit Intent and question/answer extras.
- Quiz position, answers, and answer-reveal state preserved across rotation using `onSaveInstanceState`.
- Viewing an answer does not penalize the score.

## Validation
Debug build and Android lint completed with zero errors. Remaining lint notices concern newer dependency and build-tool versions.

Tested on an Android API 35 emulator: correct and incorrect feedback, all six questions, expected 5/6 final result with one intentionally wrong answer, previous navigation without duplicate scoring, answer reveal and return, final-score preservation across landscape/portrait rotation, and restart. No crash occurred during those checks.

## Main files
- `app/src/main/java/com/example/geoquiz/MainActivity.kt`: quiz navigation, answers, scoring, and saved state.
- `app/src/main/java/com/example/geoquiz/CheatActivity.kt`: Intent extras and answer reveal.
- `app/src/main/java/com/example/geoquiz/Question.kt`: question model.
- `app/src/main/res/layout/`: XML layouts for both screens.
- `app/src/main/res/values/strings.xml`: question and interface text.
