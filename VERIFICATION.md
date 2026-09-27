# Verification — September 24, 2026

Project: geo quiz file final. Application ID: com.siddarth.geoquizfinal.

## Results
- Debug APK build: passed.
- Four Kotlin unit tests: passed (first-answer locking, unanswered FALSE handling, mixed final score, navigation wrap).
- Four Kotlin emulator tests on GeoQuiz_Phone / Android 15 API 35: passed (eight-question completion at 7/8 and review return; rotation and recreation preserving FALSE answer and selected question; explicit review Intent and review recreation; restart cancellation and confirmation).
- Android lint: zero errors, six warnings. Warnings concern available tool/SDK/test-library updates and the backup configuration. They are not runtime test failures.
- All handwritten application and test files are Kotlin; screen layouts are XML.

## Test investigation
Two initial tests failed when Espresso's scrollTo helper did not reveal the lower buttons. A diagnostic full scroll allowed the completion flow to pass. The final tests use swipe gestures to reach the lower buttons and then verify their actions. All four device tests passed together afterward. Temporary diagnostic logging and experimental scrolling changes were removed.

## Scope and limits
Manual visual checks completed in the Android Studio embedded API 35 emulator: portrait quiz layout; visible Correct! and Incorrect! Toasts; score and answered count; disabled answer buttons after answering; NEXT; selection of question eight; rotation to landscape with retained question and score; scrolling to all lower controls; review layout and explanations in landscape and portrait; return to quiz retaining answers; restart confirmation and clearing progress. No new issue was observed in these checks. Full eight-question completion and final score are covered by the automated emulator test. No physical device or exhaustive screen-size/Android-version testing was performed. Passing these checks is not a guarantee that no defect exists.

## Run again
Use Android Studio's Gradle tasks, or run ./gradlew :app:assembleDebug :app:testDebugUnitTest :app:lintDebug :app:connectedDebugAndroidTest with an emulator connected.

No upload or Canvas resubmission was performed.
