package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val id: String,
    val name: String,
    val artistName: String,
    val artworkUrl100: String,
    val releaseDate: String?,
    val copyright: String?,
    val genre: String?,
    val url: String
)
