package com.example.data.remote.model

import com.google.gson.annotations.SerializedName

data class AlbumResponse(@SerializedName("feed") val feed: Feed)
data class Feed(
    @SerializedName("results") val results: List<Album>,
    @SerializedName("copyright") val copyright: String?
)

data class Album(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("artistName") val artistName: String?,
    @SerializedName("artworkUrl100") val artworkUrl100: String?,
    @SerializedName("releaseDate") val releaseDate: String?,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("url") val url: String?
)

data class Genre(@SerializedName("name") val name: String)