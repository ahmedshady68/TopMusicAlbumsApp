package com.example.domain.usecase

import com.example.domain.repository.AlbumRepository

class GetAlbumsUseCase(private val repository: AlbumRepository) {
    operator fun invoke() = repository.getAlbums()
}