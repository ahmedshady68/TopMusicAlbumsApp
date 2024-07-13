package com.example.data.mapper


import com.example.data.local.AlbumEntity
import com.example.domain.model.Album

fun AlbumEntity.toDomain(): Album {
    return Album(
        id = id,
        name = name,
        artistName = artistName,
        url = url,
        genre = genre ?: "N/A",
        releaseDate = releaseDate ?: "N/A",
        copyright = copyright ?: "N/A",
        artworkUrl100 = artworkUrl100
    )
}

fun Album.toEntity(): AlbumEntity {
    return AlbumEntity(
        id = id,
        name = name,
        artistName = artistName,
        url = url,
        genre = genre,
        releaseDate = releaseDate,
        copyright = copyright,
        artworkUrl100 = artworkUrl100
    )
}