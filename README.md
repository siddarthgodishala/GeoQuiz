# GeoQuiz — Ocean Atlas

Android geography quiz built with Kotlin and XML layouts.

## Open the project

1. Download **GeoQuiz.zip** from this repository (open it and choose Download raw file).
2. Extract the ZIP.
3. In Android Studio, choose **Open** and select the extracted **GeoQuiz** folder containing `settings.gradle.kts`.
4. Allow Gradle to sync, select an Android emulator or device, and press Run.

The archive contains the complete Android Studio project, including source code, XML resources, manifest, Gradle configuration, and Gradle wrapper. Machine-specific settings and build caches are excluded.

## Features

- Six geography true/false questions and correct/incorrect Toast messages.
- Next and previous navigation; each question scores only once.
- Running score, final result, and restart.
- Separate Cheat Activity launched by explicit Intent with question and answer extras.
- Saved quiz position and answers across rotation using onSaveInstanceState.

## Validation

Debug build and Android lint completed with no errors (lint warnings remain). Tested on an Android API 35 emulator: correct/incorrect feedback, all six questions, expected 5/6 final result with one intentionally wrong answer, previous navigation, answer reveal and return, final-score preservation during landscape/portrait rotation, and restart. No crash occurred during those checks.
