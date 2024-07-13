package com.example.topmusicalbumsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Album
import com.example.domain.repository.Resource
import com.example.domain.usecase.GetAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<Album>>>(Resource.Loading())
    val state: StateFlow<Resource<List<Album>>> = _state

    init {
        fetchAlbums()
    }

    fun fetchAlbums() {
        viewModelScope.launch {
            getAlbumsUseCase().collect { resource ->
                _state.value = resource
            }
        }
    }
}