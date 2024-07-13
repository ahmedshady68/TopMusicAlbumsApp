package com.example.domain.usecase

import com.example.domain.model.Album
import com.example.domain.repository.AlbumRepository
import com.example.domain.repository.Resource
import kotlinx.coroutines.flow.Flow

class GetAlbumsUseCase(private val repository: AlbumRepository) {
    operator fun invoke(): Flow<Resource<List<Album>>> {
        return repository.getAlbums()
    }
}