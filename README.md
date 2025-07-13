# Jikan4k

A kotlin auto generated client for the [Jikan API v4](https://jikan.moe/)

## Installation

Add the github repository and libray dependency to your gradle config

### Kotlin DSL

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

depencencies {
    implementation("com.github.vinarnt.Jikan4k:jikan4k:1.0.0")
}
```

## How to use

```kotlin
// Instantiate a client
val apiClient = ApiClient()

// Call an API to get a response
val response = apiClient.animes.getAnimeById(1575)

// Get the data from the response
val anime = response.body().data

println(anime.titles.first())
```
