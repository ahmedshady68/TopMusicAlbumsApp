package com.example.topmusicalbumsapp.di

import com.example.data.remote.ApiService
import com.example.data.repository.AlbumRepositoryImpl
import com.example.domain.repository.AlbumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideRepo(realm: Realm, apiService: ApiService): AlbumRepository {
        return AlbumRepositoryImpl(realm, apiService)
    }
}