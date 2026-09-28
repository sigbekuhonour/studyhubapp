# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

StudyHub is a native Android app (Kotlin, Jetpack Compose, single `app` module, minSdk 31, JDK 17) for note folders, notes, and flashcards. iOS is not supported yet; cross-platform is a long-term goal.

## Commands

```bash
./gradlew assembleDebug                 # build debug APK
./gradlew installDebug                  # install on connected device/emulator
./gradlew bundleRelease                 # release AAB (minified, R8)
./gradlew testDebugUnitTest             # JVM unit tests
./gradlew testDebugUnitTest --tests "com.honoursigbeku.studyhubapp.CoroutineTest"   # single test class
./gradlew connectedDebugAndroidTest     # instrumented tests (needs device)
./gradlew lint
```

Tests exist under both `com.honoursigbeku.studyhubapp` and a leftover `com.example.studyhubapp` package; the latter is the old namespace.

## Architecture

Layered, manually wired (no DI framework):

- `domain/` — models (`Note`, `Folder`, `Flashcard`, `User`), repository interfaces, `OnboardUserUseCase`.
- `data/repository/` — `*RepositoryImpl` classes that write to **both** local and remote: every mutation goes to Room first, then Supabase. Reads (`Flow`s) come from the local Room database only, so Room is the UI's source of truth.
- `data/datasource/local/` — Room (`StudyHubLocalDatabase`, DAOs, entities, entity↔domain mappers). Obtained via `LocalStorageDataSourceProvider.getInstance(context)`.
- `data/datasource/remote/` — Supabase Postgrest client (`RemoteStorageDataSourceProvider` singleton), DTOs with kotlinx.serialization, DTO↔domain mappers. Supabase URL/key come from `BuildConfig` fields defined in `app/build.gradle.kts`.
- Auth: `AuthRepositoryImpl` uses **Firebase Auth** (email/password + Google via Credential Manager), while app data lives in Supabase. `google-services.json` is required for Firebase builds.
- `navigation/Navigation.kt` — the composition root: constructs data sources and repositories, creates ViewModels via each ViewModel's nested `Factory`, and defines the NavHost (start destination depends on `authViewModel.isUserSignedIn()`).
- `ui/screens/<feature>/` — screen composables + a ViewModel per feature (authentication, notefolder, note, flashcards); shared widgets in `ui/component/`.

- User scoping & onboarding: rows are keyed by the Firebase `uid` (`userId`). After sign-in, `AuthRepositoryImpl.onboardUser()` saves the user to Supabase. If neither Room nor Supabase has any folders yet, it seeds the three default folders. If Room is empty but Supabase has data (e.g. a fresh install), it downloads the folders, notes and flashcards into Room. There is no other sync or conflict handling, so a Supabase write that fails after the Room write is not retried.
- Default folders `Quick Notes`, `Shared Notes`, `Deleted Notes` are identified **by title string**: `FolderDao` SQL refuses to delete/rename them, and `NoteFolderViewModel` picks icons by title. Renaming these strings requires updating all of those places.

When adding a new entity/field, update entity, DAO, DTO, both mappers, the local and remote data source interfaces + impls, and the repository.
