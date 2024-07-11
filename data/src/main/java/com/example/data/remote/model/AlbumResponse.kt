package com.example.data.remote.model

data class AlbumResponse(val feed: Feed)
data class Feed(val results: List<Album>)
data class Album(
    val id: String,
    val name: String,
    val artistName: String,
    val artworkUrl100: String,
    val releaseDate: String,
    val copyright: String,
    val genres: List<Genre>,
    val url: String
)

data class Genre(val name: String)