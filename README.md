RPG Character Directory
=============

DEVELOPER: huhx0015

### RPG CHARACTER DIRECTORY
https://github.com/user-attachments/assets/71aa22d0-ebd1-469e-8d69-ebe164e5f62b

## Description

RPG Character Directory: A simple Android application that leverages Moshi for JSON parsing to display a directory of characters from various Japanese RPG games, such as the Final Fantasy and Suikoden series.

## Notes
* This project uses Moshi to deserialize JSON into Kotlin data classes. Moshi codegen is enabled via KSP (Kotlin Symbol Processing), so you’ll need a few Gradle updates.
  * `Root build.gradle` (project-level)
    * Add the KSP Gradle plugin classpath under dependencies:
      * `classpath "com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.3.2"`
      * Add the KSP plugin under plugins:
        * `id "com.google.devtools.ksp" version "2.3.2" apply false`
  * app/build.gradle (module-level)
    * Apply the KSP plugin under plugins:
      * `id "com.google.devtools.ksp"`
  * Add Moshi + codegen dependencies under dependencies:
    * `implementation "com.squareup.moshi:moshi:1.15.2"`
    * `implementation "com.squareup.moshi:moshi-kotlin:1.15.2"`
    * `ksp "com.squareup.moshi:moshi-kotlin-codegen:1.15.2"`

* All JSON files used by this project live in app/src/main/assets.
  * To list available files in the root of the assets directory, call:
    * `context.assets.list("")`
    * Example: `JsonUtil.getJsonFileNameListFromAssetFolder`
  * To open and read an asset JSON file as a string:
    * `context.assets.open(FILE_NAME).bufferedReader().use { it.readText() }`
    * Example: `JsonUtil.getJsonCharacterDataFromAsset`

* To deserialize JSON with Moshi:
  * Build a Moshi instance via `Moshi.Builder()`
  * Register `KotlinJsonAdapterFactory()` using `.addLast(...)`
  * Create the appropriate `Type` (via Types) and `JsonAdapter` for the target model
  * Example: `JsonUtil.getJsonCharacterDataFromAsset`

* Android 15+ (API 35+) supports edge-to-edge, and Android 16 enforces it (it can’t be opted out). For Compose screens that were built before edge-to-edge became the default, this can cause content to render underneath the status bar and navigation bar (especially when 3-button navigation is enabled).
  To prevent overlap, apply the appropriate inset padding modifiers:
  * `Modifier.statusBarsPadding()` — Adds top padding equal to the status bar inset. 
  * `Modifier.navigationBarsPadding()` — Adds bottom padding equal to the navigation bar inset.

## Resources

### Architecture

* MVVM Architecture: MVVM with unidirectional data flow and a single UI state object, with some MVI-inspired ideas.
  * Jetpack Compose for UI 
  * StateFlow for reactive state 
  * Repository for data access

### Libraries

* Android Jetpack: https://developer.android.com/jetpack/
* Moshi: https://github.com/square/moshi
