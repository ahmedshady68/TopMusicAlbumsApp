package com.example.data.repository

import com.example.data.local.realm.AlbumRealmModel
import com.example.data.mapper.toDomain
import com.example.data.mapper.toRealmModel
import com.example.data.remote.ApiService
import com.example.domain.model.Album
import com.example.domain.repository.AlbumRepository
import com.example.domain.repository.Resource
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AlbumRepositoryImpl(
    private val realm: Realm,
    private val apiService: ApiService
) : AlbumRepository {
    override fun getAlbums(): Flow<Resource<List<Album>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getTopAlbums()
            val albums = response.feed.results.map { dto ->
                Album(
                    id = dto.id ?: "N/A",
                    name = dto.name ?: "N/A",
                    artistName = dto.artistName ?: "N/A",
                    artworkUrl100 = dto.artworkUrl100 ?: "N/A",
                    releaseDate = dto.releaseDate ?: "N/A",
                    copyright = response.feed.copyright ?: "N/A",
                    genre = dto.genres.firstOrNull()?.name ?: "N/A",
                    url = dto.url ?: "N/A"
                )
            }
            realm.writeBlocking {
                albums.forEach { album ->
                    copyToRealm(album.toRealmModel())
                }
            }
            emit(Resource.Success(realm.query<AlbumRealmModel>().find().map { it.toDomain() }))
        } catch (e: Exception) {
            // from cache..
            emit(Resource.Success(realm.query<AlbumRealmModel>().find().map { it.toDomain() }))
        }
    }
}