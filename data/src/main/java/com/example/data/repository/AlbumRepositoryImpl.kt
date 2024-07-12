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
                    releaseDate = entity.releaseDate ?: "N/A",
                    copyright = entity.copyright ?: "N/A",
                    genre = entity.genre ?: "N/A",
                    url = entity.url
                )
            }
        }
    }

    override suspend fun refreshAlbums() {
        val response = apiService.getTopAlbums()
        val albums = response.feed.results.map { album ->
            AlbumEntity(
                id = album.id ?: "N/A",
                name = album.name ?: "N/A",
                artistName = album.artistName ?: "N/A",
                artworkUrl100 = album.artworkUrl100 ?: "N/A",
                releaseDate = album.releaseDate ?: "N/A",
                copyright = album.copyright ?: "N/A",
                genre = album.genres.joinToString { it.name },
                url = album.url ?: "N/A"
            )
        }
        albumDao.insertAll(albums)
    }
}