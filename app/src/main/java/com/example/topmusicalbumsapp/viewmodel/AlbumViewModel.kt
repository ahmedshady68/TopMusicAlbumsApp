package com.example.topmusicalbumsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Album
import com.example.domain.usecase.GetAlbumsUseCase
import com.example.domain.usecase.RefreshAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val refreshAlbumsUseCase: RefreshAlbumsUseCase
) : ViewModel() {

    val albums: Flow<List<Album>> = getAlbumsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun refreshAlbums() {
        viewModelScope.launch {
            refreshAlbumsUseCase()
        }
    }
}