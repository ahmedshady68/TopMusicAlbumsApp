package com.example.domain.repository

import com.example.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    fun getAlbums(): Flow<Resource<List<Album>>>
}