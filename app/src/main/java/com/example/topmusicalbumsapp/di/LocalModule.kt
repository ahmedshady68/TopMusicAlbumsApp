package com.example.topmusicalbumsapp.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.AlbumDao
import com.example.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "album_database"
        ).build()
    }

    @Provides
    fun provideAlbumDao(database: AppDatabase): AlbumDao = database.albumDao()
}