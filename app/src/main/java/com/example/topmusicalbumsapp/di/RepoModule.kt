package com.example.topmusicalbumsapp.di

import com.example.data.local.AlbumDao
import com.example.data.remote.ApiService
import com.example.data.repository.AlbumRepositoryImpl
import com.example.domain.repository.AlbumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideRepo(albumDao: AlbumDao, apiService: ApiService): AlbumRepository {
        return AlbumRepositoryImpl(albumDao, apiService)
    }
}