package com.example.topmusicalbumsapp.di

import com.example.domain.repository.AlbumRepository
import com.example.domain.usecase.GetAlbumsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetAlbumsUseCase(repo: AlbumRepository): GetAlbumsUseCase {
        return GetAlbumsUseCase(repo)
    }
}