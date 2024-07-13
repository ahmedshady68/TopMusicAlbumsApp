package com.example.topmusicalbumsapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.domain.model.Album
import com.example.domain.repository.Resource
import com.example.topmusicalbumsapp.viewmodel.AlbumViewModel

@Composable
fun AlbumListScreen(viewModel: AlbumViewModel = hiltViewModel(), navController: NavController) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is Resource.Loading -> CircularProgressIndicator()
        is Resource.Error -> Text(text = "An error occurred: ${(state as Resource.Error).message}")
        is Resource.Success -> {
            val albums = (state as Resource.Success<List<Album>>).data
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(albums) { album ->
                    AlbumItem(album) {
                        navController.navigate("album_detail/${album.id}")
                    }
                }
            }
        }
    }
}


@Composable
fun AlbumItem(album: Album, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(album.artworkUrl100),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = album.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = album.artistName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}