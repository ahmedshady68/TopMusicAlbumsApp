# Music App

## Overview
This is a sample Android app that displays the top 100 music albums across all genres using Apple's RSS generator. The app uses modern Android development tools and libraries, including Jetpack Compose, Realm, Dagger Hilt, and Kotlin Coroutines with Flow.

## Features
- Displays a grid of music albums with album name, artist, and album art.
- Tapping on an album opens a detailed view with additional information.
- Caches data locally using Realm for offline access.
- Handles network errors gracefully.

## Architecture
The app is structured into three modules: data, domain, and presentation. It follows the MVVM architecture pattern.

- **Data**: Contains data sources (API and local database) and repository implementation.
- **Domain**: Contains use cases and domain models.
- **Presentation**: Contains UI components, ViewModel, and DI setup.

## Libraries Used
- Jetpack Compose
- Realm
- Dagger Hilt
- Kotlin Coroutines and Flow
- Retrofit
- Coil for image loading

## How to Build
1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or device.

## Screenshots
<img src="https://github.com/user-attachments/assets/18fc6caa-c7d9-4ea1-9d58-4e514de046d5" width= "200">
<img src="https://github.com/user-attachments/assets/023b25a3-5a6a-496d-a419-43e7b84b7eef" width= "200">
<img src="https://github.com/user-attachments/assets/eaa73a40-ff54-422c-b387-e0dcc2a7c8b5" width= "200">
<img src="https://github.com/user-attachments/assets/26d6b6e0-ec55-4d18-bc2f-0da6429920dc" width= "200">

## Video
- **Video Link**: [screen recording of the app showing all features](https://www.youtube.com/watch?v=cwLQHInxp_E).
