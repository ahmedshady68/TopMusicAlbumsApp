package com.example.topmusicalbumsapp.di

import com.example.data.local.realm.AlbumRealmModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideRealmDatabase(): Realm {
        return Realm.open(
            RealmConfiguration.Builder(schema = setOf(AlbumRealmModel::class)).build()
        )
    }
}