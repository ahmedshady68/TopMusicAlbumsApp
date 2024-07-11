package com.example.data.repository

import com.example.data.local.AlbumDao
import com.example.data.local.AlbumEntity
import com.example.data.remote.ApiService
import com.example.domain.model.Album
import com.example.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlbumRepositoryImpl(
    private val albumDao: AlbumDao,
    private val apiService: ApiService
) : AlbumRepository {

    override fun getAlbums(): Flow<List<Album>> {
        return albumDao.getAllAlbums().map { entities ->
            entities.map { entity ->
                Album(
                    id = entity.id,
                    name = entity.name,
                    artistName = entity.artistName,
                    artworkUrl100 = entity.artworkUrl100,
                    releaseDate = entity.releaseDate,
                    copyright = entity.copyright,
                    genre = entity.genre,
                    url = entity.url
                )
            }
        }
    }

    override suspend fun refreshAlbums() {
        val response = apiService.getTopAlbums()
        val albums = response.feed.results.map { album ->
            AlbumEntity(
                id = album.id,
                name = album.name,
                artistName = album.artistName,
                artworkUrl100 = album.artworkUrl100,
                releaseDate = album.releaseDate,
                copyright = album.copyright,
                genre = album.genres.joinToString { it.name },
                url = album.url
            )
        }
        albumDao.insertAll(albums)
    }
}