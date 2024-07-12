package com.example.topmusicalbumsapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.domain.model.Album
import com.example.topmusicalbumsapp.viewmodel.AlbumViewModel

@Composable
fun AlbumListScreen(navController: NavController, viewModel: AlbumViewModel) {
    val albums by viewModel.albums.collectAsState(
        listOf()
    )

    LaunchedEffect(Unit) {
        viewModel.refreshAlbums()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(albums.size) { index ->
            AlbumItem(albums[index]) {
                navController.navigate("album_detail/${albums[index].id}")
            }
        }
    }
}

@Composable
fun AlbumItem(album: Album, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberImagePainter(data = album.artworkUrl100),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(album.name, style = MaterialTheme.typography.labelSmall, maxLines = 1)
        Text(album.artistName, style = MaterialTheme.typography.bodySmall, maxLines = 1)
    }
}