package com.example.domain.usecase

import com.example.domain.repository.AlbumRepository

class RefreshAlbumsUseCase(private val repository: AlbumRepository) {
    suspend operator fun invoke() = repository.refreshAlbums()
}