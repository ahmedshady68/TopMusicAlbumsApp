package com.example.data.remote

import com.example.data.remote.model.AlbumResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/v2/us/music/most-played/100/albums.json")
    suspend fun getTopAlbums(): AlbumResponse
}