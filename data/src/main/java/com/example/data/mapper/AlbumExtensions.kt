package com.example.data.mapper

import com.example.data.local.realm.AlbumRealmModel
import com.example.domain.model.Album

fun AlbumRealmModel.toDomain(): Album {
    return Album(
        id = id,
        name = name,
        artistName = artist,
        artworkUrl100 = thumbnailUrl,
        genre = genre ?: "N/A",
        releaseDate = releaseDate ?: "N/A",
        copyright = copyright ?: "N/A",
        url = url ?: "N/A"
    )
}

fun Album.toRealmModel(): AlbumRealmModel {
    return AlbumRealmModel().apply {
        id = this@toRealmModel.id
        name = this@toRealmModel.name
        artist = this@toRealmModel.artistName
        thumbnailUrl = this@toRealmModel.artworkUrl100
        genre = this@toRealmModel.genre
        releaseDate = this@toRealmModel.releaseDate
        copyright = this@toRealmModel.copyright
        url = this@toRealmModel.url
    }
}